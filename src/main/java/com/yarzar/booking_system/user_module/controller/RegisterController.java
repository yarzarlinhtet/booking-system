package com.yarzar.booking_system.user_module.controller;

import com.yarzar.booking_system.core_module.common.annotation.ApiToken;
import com.yarzar.booking_system.core_module.common.constant.AppConstant;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import com.yarzar.booking_system.user_module.controller.request.EmailVerifyRequest;
import com.yarzar.booking_system.user_module.controller.request.RegisterRequest;
import com.yarzar.booking_system.user_module.controller.response.RegisterResponse;
import com.yarzar.booking_system.user_module.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    private final IAuthService authService;

    public RegisterController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        LOG.info("Register endpoint called with request: {}", registerRequest);

        RegisterResponse registerResponse = authService.register(registerRequest);

        Map<String, Object> data = new HashMap<>();
        data.put("info", registerResponse);

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verify(@RequestBody EmailVerifyRequest emailVerifyRequest) {
        LOG.info("Verify endpoint called with request: {}", emailVerifyRequest);

        boolean isVerified = authService.verify(emailVerifyRequest);

        Map<String, Object> data = new HashMap<>();

        return HttpResponse.success(AppConstant.SUCCESS_MESSAGE, data);
    }
}
