package com.yarzar.booking_system.class_module.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarzar.booking_system.core_module.entity.Classes;

import java.time.Instant;
import java.util.UUID;

public class ClassResponse {

    @JsonProperty("class_id")
    private UUID classId;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("start_at")
    private Instant startAt;

    @JsonProperty("end_at")
    private Instant endAt;

    @JsonProperty("max_capacity")
    private Integer maxCapacity;

    @JsonProperty("credit_amount")
    private Integer creditAmount;

    public ClassResponse(Classes classes) {
        this.classId = classes.getId();
        this.countryCode = classes.getCountryCode();
        this.title = classes.getTitle();
        this.description = classes.getDescription();
        this.startAt = classes.getStartAt();
        this.endAt = classes.getEndAt();
        this.maxCapacity = classes.getMaxCapacity();
        this.creditAmount = classes.getCreditAmount();
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }
}
