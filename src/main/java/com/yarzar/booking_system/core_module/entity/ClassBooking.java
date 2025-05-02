package com.yarzar.booking_system.core_module.entity;

import com.yarzar.booking_system.core_module.common.enums.BookingStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "class_booking")
public class ClassBooking extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus status;

    @Column(name = "booked_at")
    private Instant bookedAt;

    @Column(name = "refund_at")
    private Instant refundAt;

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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }

    public Instant getRefundAt() {
        return refundAt;
    }

    public void setRefundAt(Instant refundAt) {
        this.refundAt = refundAt;
    }
}
