package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.entity.PasswordReset;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordResetRepository extends BaseRepository<PasswordReset, UUID> {

}
