package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.entity.ClassCreditTransaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassCreditTransactionRepository extends BaseRepository<ClassCreditTransaction, UUID> {

}
