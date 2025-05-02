package com.yarzar.booking_system.core_module.entity;

import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "password_resets")
public class PasswordReset extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expire_at")
    private Instant expireAt;

    @Column(name = "is_used", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isUsed;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }
}
