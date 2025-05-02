package com.yarzar.booking_system.booking_module.service;

import com.yarzar.booking_system.core_module.entity.ClassCreditTransaction;
import com.yarzar.booking_system.core_module.exception.BadRequestException;
import com.yarzar.booking_system.core_module.repository.*;
import com.yarzar.booking_system.core_module.utils.Builder;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClassCreditTransactionServiceImpl implements IClassCreditTransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassCreditTransactionServiceImpl.class);

    private final UserRepository userRepository;

    private final ClassesRepository classesRepository;

    private final ClassPackageRepository classPackageRepository;

    private final UserPackageRepository userPackageRepository;

    private final ClassCreditTransactionRepository classCreditTransactionRepository;

    public ClassCreditTransactionServiceImpl(UserRepository userRepository,
                                             ClassesRepository classesRepository,
                                             ClassPackageRepository classPackageRepository,
                                             ClassCreditTransactionRepository classCreditTransactionRepository,
                                             UserPackageRepository userPackageRepository) {
        this.userRepository = userRepository;
        this.classesRepository = classesRepository;
        this.classPackageRepository = classPackageRepository;
        this.classCreditTransactionRepository = classCreditTransactionRepository;
        this.userPackageRepository = userPackageRepository;
    }

    @Transactional
    @Override
    public void useClassCreditTransaction(UUID userId, UUID classId, UUID userPackageId) {
        LOG.info("Using class credit transaction for userId: {}, classId: {}, userPackageId: {}", userId, classId, userPackageId);

        var classes = this.classesRepository.findById(classId)
                .orElseThrow(() -> new BadRequestException("Error", "Class not found"));

        var userPackage = this.userPackageRepository.findById(userPackageId)
                .orElseThrow(() -> new BadRequestException("Error", "Package not found"));

        if (userPackage.getDefaultCreditAmount() - userPackage.getUsedCreditAmount() < classes.getCreditAmount()) {
            throw new BadRequestException("Error", "Not enough credit in package");
        }

        userPackage.setUsedCreditAmount(userPackage.getUsedCreditAmount() + classes.getCreditAmount());

        this.userPackageRepository.save(userPackage);

        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Error", "User not found"));

        var classCreditTransaction = Builder.of(ClassCreditTransaction::new)
                .add(ClassCreditTransaction::setUser, user)
                .add(ClassCreditTransaction::setClasses, classes)
                .add(ClassCreditTransaction::setCreditAmount, classes.getCreditAmount())
                .add(ClassCreditTransaction::setCreditUsed, true)
                .add(ClassCreditTransaction::setRefunded, false)
                .build();

        this.classCreditTransactionRepository.save(classCreditTransaction);

    }

    @Transactional
    @Override
    public void refundClassCreditTransaction(UUID userId, UUID classId, UUID userPackageId) {
        LOG.info("Refunding class credit transaction for userId: {}, classId: {}, userPackageId: {}", userId, classId, userPackageId);

        var classes = this.classesRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));

        var userPackage = this.userPackageRepository.findById(userPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found with ID: " + userPackageId));

        userPackage.setUsedCreditAmount(userPackage.getUsedCreditAmount() - classes.getCreditAmount());

        this.userPackageRepository.save(userPackage);

        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        var classCreditTransaction = Builder.of(ClassCreditTransaction::new)
                .add(ClassCreditTransaction::setUser, user)
                .add(ClassCreditTransaction::setClasses, classes)
                .add(ClassCreditTransaction::setCreditAmount, classes.getCreditAmount())
                .add(ClassCreditTransaction::setCreditUsed, false)
                .add(ClassCreditTransaction::setRefunded, true)
                .build();

        this.classCreditTransactionRepository.save(classCreditTransaction);
    }
}
