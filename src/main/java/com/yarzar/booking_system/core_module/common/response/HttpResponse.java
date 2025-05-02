package com.yarzar.booking_system.core_module.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Map;

public class HttpResponse {

    public static ResponseEntity<?> build(ApiResponse body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    public static ResponseEntity<?> success(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.OK.value(),
                        message,
                        data),
                HttpStatus.OK);
    }

    public static ResponseEntity<?> created(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.CREATED.value(),
                        message,
                        data),
                HttpStatus.CREATED);
    }

    public static ResponseEntity<?> badRequest(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        data),
                HttpStatus.BAD_REQUEST);
    }
}
