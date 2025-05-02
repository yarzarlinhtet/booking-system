package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.entity.ClassPackage;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassPackageRepository extends BaseRepository<ClassPackage, UUID> {
}
