package com.yarzar.booking_system.booking_module.service;

import com.yarzar.booking_system.core_module.security.CustomUserDetail;

import java.util.UUID;

public interface IBookingService {
    boolean bookClass(CustomUserDetail userDetail, UUID classId);

    boolean addToWaitList(CustomUserDetail userDetail, UUID classId);

    boolean cancelBooking(CustomUserDetail userDetail, UUID bookingId);

    void waitListToBooking(UUID classId);

    boolean refundWaitList(UUID classId);
}
