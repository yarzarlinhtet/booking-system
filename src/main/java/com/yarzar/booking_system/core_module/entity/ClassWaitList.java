package com.yarzar.booking_system.core_module.entity;


import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "class_wait_list")
public class ClassWaitList extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    @Column(name = "waited_at")
    private Instant waitedAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Instant getWaitedAt() {
        return waitedAt;
    }

    public void setWaitedAt(Instant waitedAt) {
        this.waitedAt = waitedAt;
    }
}
