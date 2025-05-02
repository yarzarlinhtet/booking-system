package com.yarzar.booking_system.package_module.controller.response;

import com.yarzar.booking_system.core_module.entity.ClassPackage;

import java.util.UUID;


public class PackageResponse {
    private UUID packageId;

    private String name;

    private String description;

    private Integer creditAmount;

    private String price;

    public PackageResponse(ClassPackage classPackage) {
        this.packageId = classPackage.getId();
        this.name = classPackage.getName();
        this.description = classPackage.getDescription();
        this.creditAmount = classPackage.getCreditAmount();
        this.price = String.valueOf(classPackage.getPrice());
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
