package com.yarzar.booking_system.user_module.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarzar.booking_system.core_module.entity.UserPackage;

import java.time.Instant;

public class UserPackageResponse {

    @JsonProperty("package_name")
    private String packageName;

    @JsonProperty("package_description")
    private String packageDescription;

    @JsonProperty("default_credit_amount")
    private Integer defaultCreditAmount;

    @JsonProperty("used_credit_amount")
    private Integer usedCreditAmount;

    @JsonProperty("remaining_credit_amount")
    private Integer remainingCreditAmount;

    @JsonProperty("expire_at")
    private Instant expireAt;

    @JsonProperty("purchase_at")
    private Instant purchaseAt;

    @JsonProperty("status")
    private String status;

    public UserPackageResponse(UserPackage userPackage) {
        this.packageName = userPackage.getClassPackage().getName();
        this.packageDescription = userPackage.getClassPackage().getDescription();
        this.defaultCreditAmount = userPackage.getDefaultCreditAmount();
        this.usedCreditAmount = userPackage.getUsedCreditAmount();
        this.expireAt = userPackage.getExpireAt();
        this.purchaseAt = userPackage.getPurchaseAt();
        this.status = userPackage.getStatus().name();
        this.remainingCreditAmount = userPackage.getDefaultCreditAmount() - userPackage.getUsedCreditAmount();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Integer getDefaultCreditAmount() {
        return defaultCreditAmount;
    }

    public void setDefaultCreditAmount(Integer defaultCreditAmount) {
        this.defaultCreditAmount = defaultCreditAmount;
    }

    public Integer getUsedCreditAmount() {
        return usedCreditAmount;
    }

    public void setUsedCreditAmount(Integer usedCreditAmount) {
        this.usedCreditAmount = usedCreditAmount;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }

    public Instant getPurchaseAt() {
        return purchaseAt;
    }

    public void setPurchaseAt(Instant purchaseAt) {
        this.purchaseAt = purchaseAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRemainingCreditAmount() {
        return remainingCreditAmount;
    }

    public void setRemainingCreditAmount(Integer remainingCreditAmount) {
        this.remainingCreditAmount = remainingCreditAmount;
    }
}
