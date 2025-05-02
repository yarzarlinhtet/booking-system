package com.yarzar.booking_system.booking_module.controller;

import com.yarzar.booking_system.booking_module.controller.reqeust.BookingRequest;
import com.yarzar.booking_system.booking_module.service.IBookingService;
import com.yarzar.booking_system.core_module.common.annotation.ApiToken;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.core_module.security.annotation.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiToken
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> book(@CurrentUser CustomUserDetail customUserDetail, @RequestBody BookingRequest bookingRequest) {
        LOG.info("Booking request received: {}", bookingRequest);

        Map<String, Object> data = new HashMap<>();
        if (this.bookingService.bookClass(customUserDetail, bookingRequest.getClassId())) {
            data.put("message", "Booking successful");
        } else if (this.bookingService.addToWaitList(customUserDetail, bookingRequest.getClassId())) {
            data.put("message", "Added to waitlist");
        } else {
            data.put("message", "Booking failed");
        }

        return HttpResponse.success("Successful", data);
    }

    @ApiToken
    @PostMapping(value = "/refund/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelBooking(@CurrentUser CustomUserDetail customUserDetail, @PathVariable(name = "bookingId") UUID bookingId) {
        LOG.info("Refund booking request received: bookingId : {}", bookingId);

        Map<String, Object> data = new HashMap<>();
        if (this.bookingService.cancelBooking(customUserDetail, bookingId)) {
            data.put("message", "Booking refunded successfully");
        } else {
            data.put("message", "Refunded failed");
        }

        return HttpResponse.success("Successful", data);
    }
}
