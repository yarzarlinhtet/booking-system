package com.yarzar.booking_system.user_module.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Override
    public boolean sendVerificationEmail(String email, String token) {
        return true;
    }

}
