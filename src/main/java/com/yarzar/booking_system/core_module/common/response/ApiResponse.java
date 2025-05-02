package com.yarzar.booking_system.core_module.common.response;

import java.time.Instant;
import java.util.Map;

public record ApiResponse(Instant timestamp,
                          int code,
                          String message,
                          Map<String, ?> data) {
}
