package com.yarzar.booking_system.user_module.controller;

import com.yarzar.booking_system.core_module.common.annotation.ApiToken;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.core_module.security.annotation.CurrentUser;
import com.yarzar.booking_system.core_module.utils.Builder;
import com.yarzar.booking_system.user_module.controller.request.ChangePasswordRequest;
import com.yarzar.booking_system.user_module.controller.request.PurchasePackageRequest;
import com.yarzar.booking_system.user_module.controller.response.ProfileResponse;
import com.yarzar.booking_system.user_module.service.IAuthService;
import com.yarzar.booking_system.user_module.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final IAuthService authService;

    private final IUserService userService;

    public UserController(IAuthService authService, IUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @ApiToken
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> me(@CurrentUser CustomUserDetail currentUser) {
        LOG.info("User endpoint called with request: {}", currentUser);

        Map<String, Object> data = Map.of(
                "profile", Builder.of(ProfileResponse::new)
                        .add(ProfileResponse::setName, currentUser.getName())
                        .add(ProfileResponse::setEmail, currentUser.getUsername())
                        .add(ProfileResponse::setVerified, currentUser.isEnabled())
                        .build()
        );

        return HttpResponse.success("Successful", data);
    }

    @ApiToken
    @GetMapping(value = "/packages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPackages(@CurrentUser CustomUserDetail currentUser) {
        LOG.info("Get packages endpoint called with request: {}", currentUser);

        Map<String, Object> data = Map.of(
                "packages", this.userService.getUserPackages(currentUser)
        );

        return HttpResponse.success("Successful", data);
    }

    @ApiToken
    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@CurrentUser CustomUserDetail currentUser,
                                            @RequestBody ChangePasswordRequest changePasswordRequest) {
        LOG.info("Change password endpoint called with request: {}", currentUser);

        this.authService.changePassword(currentUser, changePasswordRequest);

        Map<String, Object> data = Map.of(
                "message", "Password changed successfully"
        );

        return HttpResponse.success("Successful", data);
    }

    @ApiToken
    @PostMapping(value = "/buy-package", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buyPackage(@CurrentUser CustomUserDetail currentUser,
                                        @RequestBody PurchasePackageRequest purchasePackageRequest) {
        LOG.info("Buy package endpoint called with request: email {}", currentUser.getUsername());

        boolean isSuccess = this.userService.buyClassPackage(currentUser, purchasePackageRequest);

        Map<String, Object> data = new HashMap<>();

        if (isSuccess) {
            data.put("message", "Package purchased successfully");
            return HttpResponse.success("Successful", data);
        } else {
            data.put("message", "Failed to purchase package");
            return HttpResponse.badRequest("Failed", data);
        }

    }

    @ApiToken
    @GetMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookings(@CurrentUser CustomUserDetail currentUser) {
        LOG.info("Get bookings endpoint called with request: {}", currentUser);

        Map<String, Object> data = Map.of(
                "bookings", this.userService.getUserBookings(currentUser)
        );

        return HttpResponse.success("Successful", data);
    }

    @ApiToken
    @PostMapping(value = "/classes/{bookingId}/check-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkIn(@CurrentUser CustomUserDetail currentUser,
                                     @PathVariable(name = "bookingId") UUID bookingId) {
        LOG.info("Check-in endpoint called with request: {}", currentUser);

        Map<String, Object> data = Map.of(
                "message", this.userService.makeCheckIn(currentUser, bookingId)
        );

        return HttpResponse.success("Successful", data);
    }
}
