package com.yarzar.booking_system.user_module.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarzar.booking_system.core_module.entity.ClassBooking;

import java.time.Instant;
import java.util.UUID;

public class UserBookingResponse {

    @JsonProperty("booking_id")
    private UUID bookingId;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("description")
    private String description;

    @JsonProperty("class_start")
    private Instant classStart;

    @JsonProperty("class_end")
    private Instant classEnd;

    @JsonProperty("booking_status")
    private String bookingStatus;

    @JsonProperty("booked_at")
    private Instant bookedAt;

    @JsonProperty("refund_at")
    private Instant refundAt;

    public UserBookingResponse(ClassBooking classBooking) {
        this.bookingId = classBooking.getId();
        this.className = classBooking.getClasses().getTitle();
        this.description = classBooking.getClasses().getDescription();
        this.classStart = classBooking.getClasses().getStartAt();
        this.classEnd = classBooking.getClasses().getEndAt();
        this.bookingStatus = classBooking.getStatus().name();
        this.bookedAt = classBooking.getBookedAt();
        this.refundAt = classBooking.getRefundAt();
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getClassStart() {
        return classStart;
    }

    public void setClassStart(Instant classStart) {
        this.classStart = classStart;
    }

    public Instant getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(Instant classEnd) {
        this.classEnd = classEnd;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
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
