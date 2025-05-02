package com.yarzar.booking_system.user_module.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailVerifyRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
