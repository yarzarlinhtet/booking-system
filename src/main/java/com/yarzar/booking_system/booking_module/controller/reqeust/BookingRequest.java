package com.yarzar.booking_system.booking_module.controller.reqeust;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class BookingRequest {

    @NotEmpty(message = "Class ID cannot be empty")
    @JsonProperty("class_id")
    private UUID classId;

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }
}
