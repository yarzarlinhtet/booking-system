package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.common.enums.PackageStatus;
import com.yarzar.booking_system.core_module.entity.UserPackage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPackageRepository extends BaseRepository<UserPackage, UUID> {

    Optional<UserPackage> findByUserIdAndClassPackageId(UUID userId, UUID classPackageId);

   List<UserPackage> findByUserIdAndClassPackageCountryCodeAndStatus(UUID userId, String countryCode, PackageStatus status);
}
