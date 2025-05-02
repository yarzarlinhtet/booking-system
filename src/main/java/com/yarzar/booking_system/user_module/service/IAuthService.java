package com.yarzar.booking_system.user_module.service;

import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.user_module.controller.request.ChangePasswordRequest;
import com.yarzar.booking_system.user_module.controller.request.EmailVerifyRequest;
import com.yarzar.booking_system.user_module.controller.request.RegisterRequest;
import com.yarzar.booking_system.user_module.controller.response.AuthResponse;
import com.yarzar.booking_system.user_module.controller.response.RegisterResponse;
import org.springframework.security.core.Authentication;

public interface IAuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    boolean verify(EmailVerifyRequest emailVerifyRequest);

    AuthResponse login(String email, String password);

    AuthResponse login(Authentication authentication);

    boolean changePassword(CustomUserDetail customUserDetail, ChangePasswordRequest changePasswordRequest);
}
