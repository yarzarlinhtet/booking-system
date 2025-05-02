package com.yarzar.booking_system.core_module.repository;

import com.yarzar.booking_system.core_module.common.enums.BookingStatus;
import com.yarzar.booking_system.core_module.entity.ClassBooking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassBookingRepository extends BaseRepository<ClassBooking, UUID> {
    Integer countClassBookingByClassesIdAndStatus(UUID classId, BookingStatus status);

    boolean existsByClassesIdAndUserIdAndStatus(UUID classId, UUID userId, BookingStatus status);

    List<ClassBooking> findAllByClassesIdAndStatus(UUID classId, BookingStatus status);
}
