package com.yarzar.booking_system.user_module.service;


import com.yarzar.booking_system.core_module.common.enums.BookingStatus;
import com.yarzar.booking_system.core_module.common.enums.PackageStatus;
import com.yarzar.booking_system.core_module.entity.ClassBooking;
import com.yarzar.booking_system.core_module.entity.User;
import com.yarzar.booking_system.core_module.entity.UserPackage;
import com.yarzar.booking_system.core_module.exception.BadRequestException;
import com.yarzar.booking_system.core_module.repository.ClassBookingRepository;
import com.yarzar.booking_system.core_module.repository.ClassPackageRepository;
import com.yarzar.booking_system.core_module.repository.UserPackageRepository;
import com.yarzar.booking_system.core_module.repository.UserRepository;
import com.yarzar.booking_system.core_module.repository.specification.UserBookingSpecification;
import com.yarzar.booking_system.core_module.repository.specification.UserPackageSpecification;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.core_module.utils.Builder;
import com.yarzar.booking_system.user_module.controller.request.PurchasePackageRequest;
import com.yarzar.booking_system.user_module.controller.response.UserBookingResponse;
import com.yarzar.booking_system.user_module.controller.response.UserPackageResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final ClassPackageRepository classPackageRepository;

    private final UserPackageRepository userPackageRepository;

    private final ClassBookingRepository classBookingRepository;

    private static final int CLASS_EXPIRE_DAYS = 30;

    public UserServiceImpl(UserRepository userRepository, ClassPackageRepository classPackageRepository, UserPackageRepository userPackageRepository, ClassBookingRepository classBookingRepository) {
        this.userRepository = userRepository;
        this.classPackageRepository = classPackageRepository;
        this.userPackageRepository = userPackageRepository;
        this.classBookingRepository = classBookingRepository;
    }

    @Transactional
    @Override
    public boolean buyClassPackage(CustomUserDetail userDetail, PurchasePackageRequest purchasePackageRequest) {
        LOG.info("Buying class package for user: {}", userDetail.getUsername());

        var classPackage = this.classPackageRepository.findById(purchasePackageRequest.getPackageId())
                .orElseThrow(() -> new BadRequestException("ERROR", "Class package not found."));

        User user = this.userRepository.findByEmail(userDetail.getUsername())
                .orElseThrow(() -> new BadRequestException("ERROR", "User not found."));

        Optional<UserPackage> existingPackage = this.userPackageRepository.findByUserIdAndClassPackageId(user.getId(), classPackage.getId());

        if (!this.makePayment(userDetail, purchasePackageRequest.getPackageId(), purchasePackageRequest.getPaymentMethod())) {
            throw new BadRequestException("ERROR", "Payment failed.");
        }

        if (existingPackage.isPresent()) {
            LOG.info("Extending existing package for user: {} package: {}", userDetail.getUsername(), classPackage.getName());

            UserPackage userPackage = existingPackage.get();
            userPackage.setStatus(PackageStatus.active);
            userPackage.setDefaultCreditAmount(classPackage.getCreditAmount() + userPackage.getDefaultCreditAmount());
            userPackage.setPurchaseAt(Instant.now());
            userPackage.setExpireAt(Instant.now().plus(CLASS_EXPIRE_DAYS, java.time.temporal.ChronoUnit.DAYS));

            this.userPackageRepository.save(userPackage);
        } else {
            LOG.info("Creating new package for user: {} package: {}", userDetail.getUsername(), classPackage.getName());
            UserPackage userPackage = Builder.of(UserPackage::new)
                    .add(UserPackage::setUser, user)
                    .add(UserPackage::setClassPackage, classPackage)
                    .add(UserPackage::setStatus, PackageStatus.active)
                    .add(UserPackage::setDefaultCreditAmount, classPackage.getCreditAmount())
                    .add(UserPackage::setUsedCreditAmount, 0)
                    .add(UserPackage::setPurchaseAt, Instant.now())
                    .add(UserPackage::setExpireAt, Instant.now().plus(CLASS_EXPIRE_DAYS, java.time.temporal.ChronoUnit.DAYS))
                    .build();

            this.userPackageRepository.save(userPackage);
        }

        return true;
    }

    @Override
    public boolean makePayment(CustomUserDetail userDetail, UUID packageId, String paymentMethod) {
        if (!StringUtils.hasText(paymentMethod)) {
            LOG.error("Payment method is empty");
            throw new BadRequestException("ERROR", "Unsupported payment method");
        }
        return true;
    }

    @Override
    public List<UserPackageResponse> getUserPackages(CustomUserDetail userDetail) {
        return this.userPackageRepository.findAll(Specification.where(UserPackageSpecification.hasUser(userDetail.getUsername())), Sort.sort(UserPackage.class).by(UserPackage::getPurchaseAt).descending())
                .stream()
                .filter(userPackage -> userPackage.getUser().getEmail().equals(userDetail.getUsername()))
                .map(UserPackageResponse::new)
                .toList();
    }

    @Override
    public List<UserBookingResponse> getUserBookings(CustomUserDetail userDetail) {
        return this.classBookingRepository.findAll(UserBookingSpecification.hasUser(userDetail.getUsername()), Sort.sort(ClassBooking.class).by(ClassBooking::getUpdatedDate).descending())
                .stream()
                .map(UserBookingResponse::new)
                .toList();
    }

    @Override
    public boolean makeCheckIn(CustomUserDetail userDetail, UUID bookingId) {
        LOG.info("Making check-in for user: {} bookingId: {}", userDetail.getUsername(), bookingId);

        var booking = this.classBookingRepository.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("ERROR", "Booking not found."));

        if (Objects.equals(BookingStatus.checked_in, booking.getStatus())) {
            throw new BadRequestException("ERROR", "Booking already checked in.");
        }

        if (Objects.equals(BookingStatus.refunded, booking.getStatus())) {
            throw new BadRequestException("ERROR", "Booking already refunded.");
        }

        booking.setStatus(BookingStatus.checked_in);
        this.classBookingRepository.save(booking);

        return true;
    }
}
