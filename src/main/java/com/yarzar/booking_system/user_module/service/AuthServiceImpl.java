package com.yarzar.booking_system.user_module.service;

import com.yarzar.booking_system.core_module.entity.User;
import com.yarzar.booking_system.core_module.exception.BadRequestException;
import com.yarzar.booking_system.core_module.repository.UserRepository;
import com.yarzar.booking_system.core_module.security.CustomUserDetail;
import com.yarzar.booking_system.core_module.security.JwtTokenProvider;
import com.yarzar.booking_system.core_module.security.TokenPayload;
import com.yarzar.booking_system.core_module.utils.AppUtils;
import com.yarzar.booking_system.core_module.utils.Builder;
import com.yarzar.booking_system.user_module.controller.request.ChangePasswordRequest;
import com.yarzar.booking_system.user_module.controller.request.EmailVerifyRequest;
import com.yarzar.booking_system.user_module.controller.request.RegisterRequest;
import com.yarzar.booking_system.user_module.controller.response.AuthResponse;
import com.yarzar.booking_system.user_module.controller.response.RegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthServiceImpl implements IAuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final INotificationService notificationService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           INotificationService notificationService,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        LOG.info("register() registerRequest: {}", registerRequest);

        if (!Objects.equals(registerRequest.getPassword(), registerRequest.getConfirmPassword())) {
            throw new BadRequestException("ERROR", "Password and confirm password do not match");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("ERROR", "Email already exists");
        }

        try {
            // Generate verification token
            String verificationToken = AppUtils.generateVerificationToken(registerRequest.getEmail());

            User user = Builder.of(User::new)
                    .add(User::setName, registerRequest.getName())
                    .add(User::setEmail, registerRequest.getEmail())
                    .add(User::setPassword, this.passwordEncoder.encode(registerRequest.getPassword()))
                    .add(User::setVerificationToken, verificationToken)
                    .add(User::setVerified, false)
                    .build();

            this.userRepository.save(user);

            this.notificationService.sendVerificationEmail(registerRequest.getEmail(), verificationToken);

            return Builder.of(RegisterResponse::new)
                    .add(RegisterResponse::setName, user.getName())
                    .add(RegisterResponse::setEmail, user.getEmail())
                    .build();
        } catch (Exception e) {
            LOG.error("Error occurred while registering user: {}", e.getMessage());
            throw new BadRequestException("ERROR", "Failed to register user");
        }

    }

    @Override
    public boolean verify(EmailVerifyRequest emailVerifyRequest) {
        LOG.info("verify() emailVerifyRequest: {}", emailVerifyRequest);

        User user = this.userRepository.findByEmail(emailVerifyRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("ERROR", "User not found"));

        if (user.getVerified()) {
            throw new BadRequestException("ERROR", "User already verified");
        }

        if (!checkValidationToken(user, emailVerifyRequest.getToken())) {
            throw new BadRequestException("ERROR", "Invalid verification token");
        }

        user.setVerified(true);
        user.setVerificationToken("-");
        this.userRepository.save(user);

        return true;
    }

    @Override
    public AuthResponse login(String email, String password) {
        LOG.info("login() email: {}", email);

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();

        return this.jwtTokenProvider.generateToken(
                Builder.of(TokenPayload::new)
                        .add(TokenPayload::setEmail, customUserDetail.getUsername())
                        .add(TokenPayload::setAuthorities, authentication.getAuthorities())
                        .build()
        );
    }

    @Override
    public AuthResponse login(Authentication authentication) {
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();

        return this.jwtTokenProvider.generateToken(
                Builder.of(TokenPayload::new)
                        .add(TokenPayload::setEmail, customUserDetail.getUsername())
                        .add(TokenPayload::setAuthorities, authentication.getAuthorities())
                        .build()
        );
    }

    @Override
    public boolean changePassword(CustomUserDetail customUserDetail, ChangePasswordRequest changePasswordRequest) {
        LOG.info("changePassword() customUserDetail: email: {}", customUserDetail.getUsername());

        if (Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getOldPassword())) {
            throw new BadRequestException("ERROR", "Old password and new password much be different");
        }

        if (!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmPassword())) {
            throw new BadRequestException("ERROR", "New password and confirm password do not match");
        }

        if (!this.passwordEncoder.matches(changePasswordRequest.getOldPassword(), customUserDetail.getPassword())) {
            throw new BadRequestException("ERROR", "Old password is incorrect");
        }

        User user = this.userRepository.findByEmail(customUserDetail.getUsername())
                .orElseThrow(() -> new BadRequestException("ERROR", "User not found"));

        try {
            user.setPassword(this.passwordEncoder.encode(changePasswordRequest.getNewPassword()));

            this.userRepository.save(user);

            return true;
        } catch (Exception e) {
            LOG.error("Error occurred while sending verification email: {}", e.getMessage());
            throw new BadRequestException("ERROR", "Failed to change password");
        }
    }

    private boolean checkValidationToken(User user, String token) {
        // Implement token validation logic here
        return true;
    }
}
