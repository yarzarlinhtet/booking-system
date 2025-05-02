package com.yarzar.booking_system.core_module.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class_credit_transaction")
public class ClassCreditTransaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    @Column(name = "credit_amount", nullable = false)
    private Integer creditAmount;

    @Column(name = "credit_used", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean creditUsed;

    @Column(name = "refunded", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean refunded;

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

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Boolean getCreditUsed() {
        return creditUsed;
    }

    public void setCreditUsed(Boolean creditUsed) {
        this.creditUsed = creditUsed;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }
}
