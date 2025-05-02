package com.yarzar.booking_system.core_module.security;

import com.yarzar.booking_system.user_module.controller.response.AuthResponse;

public interface JwtTokenProvider {
    AuthResponse generateToken(TokenPayload tokenPayload);

    TokenPayload getPayloadFromToken(String token);

    boolean validateToken(String token);
}
