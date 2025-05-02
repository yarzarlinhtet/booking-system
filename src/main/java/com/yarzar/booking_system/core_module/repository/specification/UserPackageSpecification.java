package com.yarzar.booking_system.core_module.repository.specification;

import com.yarzar.booking_system.core_module.entity.UserPackage;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserPackageSpecification {
    public static Specification<UserPackage> hasUser(String email) {
        return (root, query, cb) -> email == null ? null : cb.equal(root.get("user").get("email"), email);
    }

    public static Specification<UserPackage> hasUser(UUID userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }
}
