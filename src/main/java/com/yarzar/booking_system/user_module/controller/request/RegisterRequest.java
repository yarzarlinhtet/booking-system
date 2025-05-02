package com.yarzar.booking_system.user_module.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public class RegisterRequest {
    @NotEmpty(message = "First name is required")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "Email is required")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Password is required")
    @JsonProperty("password")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
