package com.yarzar.booking_system.core_module.entity;

import com.yarzar.booking_system.core_module.common.enums.PackageStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user_packages")
public class UserPackage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classes_package_id")
    private ClassPackage classPackage;

    @Column(name = "default_credit_amount")
    private Integer defaultCreditAmount;

    @Column(name = "used_credit_amount")
    private Integer usedCreditAmount;

    @Column(name = "expire_at")
    private Instant expireAt;

    @Column(name = "purchase_at")
    private Instant purchaseAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'active'")
    private PackageStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassPackage getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(ClassPackage classPackage) {
        this.classPackage = classPackage;
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

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }
}
