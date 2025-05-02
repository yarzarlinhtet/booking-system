package com.yarzar.booking_system.user_module.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class PurchasePackageRequest {
    @NotEmpty(message = "Package ID cannot be empty")
    @JsonProperty("package_id")
    private UUID packageId;

    @NotEmpty(message = "Payment method cannot be empty")
    @JsonProperty("payment_method")
    private String paymentMethod;

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
