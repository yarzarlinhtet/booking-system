package com.yarzar.booking_system.booking_module.service;

import java.util.UUID;

public interface IClassCreditTransactionService {
    void useClassCreditTransaction(UUID userId, UUID classId, UUID packageId);

    void refundClassCreditTransaction(UUID userId, UUID classId, UUID packageId);
}
