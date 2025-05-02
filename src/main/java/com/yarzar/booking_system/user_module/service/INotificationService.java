package com.yarzar.booking_system.user_module.service;

public interface INotificationService {
    boolean sendVerificationEmail(String email, String token);
}
