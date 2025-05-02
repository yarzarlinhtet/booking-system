package com.yarzar.booking_system.user_module.controller;

import com.yarzar.booking_system.core_module.common.annotation.BasicAuth;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import com.yarzar.booking_system.user_module.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final IAuthService authService;

    public LoginController(IAuthService authService) {
        this.authService = authService;
    }

    @BasicAuth
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> login(Authentication authentication) {
        LOG.info("Login endpoint called with request: authenticated: {{}", authentication.isAuthenticated());

        Map<String, Object> response = new HashMap<>();
        response.put("info", authService.login(authentication));

        return HttpResponse.success("Login successful", response);
    }
}
