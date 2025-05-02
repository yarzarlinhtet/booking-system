package com.yarzar.booking_system.booking_module.service;

import com.yarzar.booking_system.core_module.common.enums.BookingStatus;
import com.yarzar.booking_system.core_module.common.enums.PackageStatus;
import com.yarzar.booking_system.core_module.entity.*;
import com.yarzar.booking_system.core_module.exception.BadRequestException;
import com.yarzar.booking_system.core_module.repository.*;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.core_module.service.LockManager;
import com.yarzar.booking_system.core_module.utils.Builder;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookingServiceImpl implements IBookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final ClassBookingRepository bookingRepository;

    private final ClassesRepository classesRepository;

    private final UserRepository userRepository;

    private final UserPackageRepository userPackageRepository;

    private final IClassCreditTransactionService classCreditTransactionService;

    private final ClassWaitListRepository classWaitListRepository;

    private final LockManager lockManager;

    public BookingServiceImpl(ClassBookingRepository bookingRepository,
                              ClassesRepository classesRepository,
                              UserRepository userRepository,
                              LockManager lockManager,
                              UserPackageRepository userPackageRepository,
                              IClassCreditTransactionService classCreditTransactionService,
                              ClassWaitListRepository classWaitListRepository) {
        this.bookingRepository = bookingRepository;
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
        this.lockManager = lockManager;
        this.userPackageRepository = userPackageRepository;
        this.classCreditTransactionService = classCreditTransactionService;
        this.classWaitListRepository = classWaitListRepository;
    }


    @Override
    public boolean bookClass(CustomUserDetail userDetail, UUID classId) {

        lockManager.getLock(classId.toString()).lock();
        lockManager.getLock(userDetail.getUsername()).lock();

        try {
            Classes classes = classesRepository.findById(classId)
                    .orElseThrow(() -> new BadRequestException("Error", "Class not found"));

            User user = userRepository.findByEmail(userDetail.getUsername())
                    .orElseThrow(() -> new BadRequestException("Error", "User not found"));

            if (this.bookingRepository.existsByClassesIdAndUserIdAndStatus(classId, user.getId(), BookingStatus.booked)) {
                LOG.info("User has already booked this class");
                throw new BadRequestException("Error", "User has already booked this class");
            }

            List<UserPackage> userPackages = userPackageRepository.findByUserIdAndClassPackageCountryCodeAndStatus(
                    user.getId(), classes.getCountryCode(), PackageStatus.active);

            if (userPackages == null || userPackages.isEmpty()) {
                LOG.info("User package not found");
                throw new BadRequestException("Error", "User package not found");
            }

            UserPackage userPackage = userPackages.stream()
                    .filter(up -> (up.getDefaultCreditAmount() - up.getUsedCreditAmount() >= classes.getCreditAmount()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Error", "No suitable user package found"));

            final int maxCapacity = classes.getMaxCapacity();

            final int bookedCount = bookingRepository.countClassBookingByClassesIdAndStatus(classId, BookingStatus.booked);

            if (bookedCount >= maxCapacity) {
                LOG.info("Class is full");
                return false;
            }

            if (classes.getStartAt().isBefore(Instant.now())) {
                LOG.info("Class has already started");
                throw new BadRequestException("Error", "Class has already started");
            }

            this.classCreditTransactionService.useClassCreditTransaction(user.getId(), classes.getId(), userPackage.getId());

            var booking = Builder.of(ClassBooking::new)
                    .add(ClassBooking::setClasses, classes)
                    .add(ClassBooking::setUser, user)
                    .add(ClassBooking::setStatus, BookingStatus.booked)
                    .add(ClassBooking::setBookedAt, Instant.now())
                    .build();

            this.bookingRepository.save(booking);

            return true;

        } finally {
            LOG.info("bookClass() Releasing locks for classId: {} and user: {}", classId, userDetail.getUsername());
            lockManager.releaseLock(classId.toString());
            lockManager.releaseLock(userDetail.getUsername());
        }

    }

    @Override
    public boolean addToWaitList(CustomUserDetail userDetail, UUID classId) {
        lockManager.getLock(classId.toString()).lock();
        lockManager.getLock(userDetail.getUsername()).lock();
        try {
            Classes classes = classesRepository.findById(classId)
                    .orElseThrow(() -> new BadRequestException("Error", "Class not found"));

            User user = userRepository.findByEmail(userDetail.getUsername())
                    .orElseThrow(() -> new BadRequestException("Error", "User not found"));

            if (this.classWaitListRepository.existsByClassesIdAndUserId(classId, user.getId())) {
                LOG.info("User is already in the waitlist for this class");
                throw new BadRequestException("Error", "User is already in the waitlist for this class");
            }

            if (classes.getStartAt().isBefore(Instant.now())) {
                LOG.info("Class has already started");
                throw new BadRequestException("Error", "Class has already started");
            }

            List<UserPackage> userPackages = userPackageRepository.findByUserIdAndClassPackageCountryCodeAndStatus(
                    user.getId(), classes.getCountryCode(), PackageStatus.active);

            if (userPackages == null || userPackages.isEmpty()) {
                LOG.info("User package not found");
                throw new BadRequestException("Error", "User package not found");
            }

            UserPackage userPackage = userPackages.stream()
                    .filter(up -> (up.getDefaultCreditAmount() - up.getUsedCreditAmount() >= classes.getCreditAmount()))
                    .findFirst()
                    .orElseThrow(() -> new BadRequestException("Error", "No suitable user package found"));

            this.classCreditTransactionService.useClassCreditTransaction(user.getId(), classes.getId(), userPackage.getId());

            var waitList = Builder.of(ClassWaitList::new)
                    .add(ClassWaitList::setClasses, classes)
                    .add(ClassWaitList::setUser, user)
                    .add(ClassWaitList::setWaitedAt, Instant.now())
                    .build();

            this.classWaitListRepository.save(waitList);

            return true;
        } finally {
            LOG.info("addToWaitList() Releasing locks for classId: {} and user: {}", classId, userDetail.getUsername());
            lockManager.releaseLock(classId.toString());
            lockManager.releaseLock(userDetail.getUsername());
        }
    }

    /**
     * Refund the booking
     *
     * @param userDetail
     * @param bookingId
     * @return
     */
    @Override
    public boolean cancelBooking(CustomUserDetail userDetail, UUID bookingId) {

        LOG.info("cancelBooking() called with bookingId: {} and user: {}", bookingId, userDetail.getUsername());

        lockManager.getLock(userDetail.getUsername()).lock();

        var booking = this.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("Error", "Booking not found"));

        var classes = booking.getClasses();

        if (Objects.equals(booking.getStatus(), BookingStatus.refunded)) {
            LOG.info("Booking is already refunded");
            throw new BadRequestException("Error", "Booking is already refunded");
        }

        if (classes.getStartAt().isBefore(Instant.now().plus(4, ChronoUnit.HOURS))) {
            LOG.info("Cannot cancel booking as the class will start within 4 hours");
            throw new BadRequestException("Error", "Cannot cancel booking as the class will start within 4 hours");
        }

        UUID classId = classes.getId();

        lockManager.getLock(classId.toString()).lock();

        try {

            User user = userRepository.findByEmail(userDetail.getUsername())
                    .orElseThrow(() -> new BadRequestException("Error", "User not found"));

            List<UserPackage> userPackages = userPackageRepository.findByUserIdAndClassPackageCountryCodeAndStatus(
                    user.getId(), classes.getCountryCode(), PackageStatus.active);

            if (userPackages == null || userPackages.isEmpty()) {
                LOG.info("User package not found");
                throw new BadRequestException("Error", "User package not found");
            }

            UserPackage userPackage = userPackages.stream().findFirst().get();

            booking.setRefundAt(Instant.now());
            booking.setStatus(BookingStatus.refunded);
            this.bookingRepository.save(booking);

            this.classCreditTransactionService.refundClassCreditTransaction(
                    user.getId(),
                    classes.getId(),
                    userPackage.getId()
            );

            this.waitListToBooking(classId);

            return true;

        } finally {
            LOG.info("cancelBooking() Releasing locks for classId: {} and user: {}", booking.getClasses().getId(), userDetail.getUsername());
            lockManager.releaseLock(classId.toString());
            lockManager.releaseLock(userDetail.getUsername());
        }
    }

    @Transactional
    @Override
    public void waitListToBooking(UUID classId) {
        LOG.info("waitListToBooking() called with classId: {} and user: {}", classId);

        Classes classes = classesRepository.findById(classId)
                .orElseThrow(() -> new BadRequestException("Error", "Class not found"));

        var waitList = this.classWaitListRepository.findFirstByClassesIdOrderByWaitedAt(classId);

        if (Objects.isNull(waitList)) {
            LOG.info("No waitlist found for classId: {}", classId);
            return;
        }

        User user = waitList.getUser();

        var booking = Builder.of(ClassBooking::new)
                .add(ClassBooking::setClasses, classes)
                .add(ClassBooking::setUser, user)
                .add(ClassBooking::setStatus, BookingStatus.booked)
                .add(ClassBooking::setBookedAt, Instant.now())
                .build();

        this.bookingRepository.save(booking);

        this.classWaitListRepository.delete(waitList);

    }

    @Override
    public boolean refundWaitList(UUID classId) {
        LOG.info("refundWaitList() called with classId: {} and user: {}", classId);

        lockManager.getLock(classId.toString()).lock();

        try {
            var waitList = this.classWaitListRepository.findAllByClassesId(classId);

            for (var waitListItem : waitList
            ) {
                var user = waitListItem.getUser();

                var classes = waitListItem.getClasses();

                List<UserPackage> userPackages = userPackageRepository.findByUserIdAndClassPackageCountryCodeAndStatus(
                        user.getId(), classes.getCountryCode(), PackageStatus.active);

                if (userPackages == null || userPackages.isEmpty()) {
                    LOG.info("User package not found");
                    throw new BadRequestException("Error", "User package not found");
                }

                UserPackage userPackage = userPackages.stream().findFirst().get();

                this.classCreditTransactionService.refundClassCreditTransaction(
                        user.getId(),
                        classes.getId(),
                        userPackage.getId()
                );

                this.classWaitListRepository.delete(waitListItem);
            }
            return true;
        } finally {
            LOG.info("refundWaitList() Releasing locks for classId: {}", classId);
            lockManager.releaseLock(classId.toString());
        }


    }
}
