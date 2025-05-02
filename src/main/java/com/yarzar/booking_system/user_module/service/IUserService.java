package com.yarzar.booking_system.user_module.service;

import com.yarzar.booking_system.core_module.entity.UserPackage;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.user_module.controller.request.PurchasePackageRequest;
import com.yarzar.booking_system.user_module.controller.response.UserBookingResponse;
import com.yarzar.booking_system.user_module.controller.response.UserPackageResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    boolean buyClassPackage(CustomUserDetail userDetail, PurchasePackageRequest purchasePackageRequest);

    boolean makePayment(CustomUserDetail userDetail, UUID packageId, String paymentMethod);

    List<UserPackageResponse> getUserPackages(CustomUserDetail userDetail);

    List<UserBookingResponse> getUserBookings(CustomUserDetail userDetail);

    boolean makeCheckIn(CustomUserDetail userDetail, UUID bookingId);
}
