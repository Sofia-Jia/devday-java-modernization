package com.example.ecommerce.model;

import java.math.BigDecimal;

public final class PayPalPayment implements Payment {
    private final BigDecimal amount;
    private PaymentStatus status;
    private final String email;

    public PayPalPayment(BigDecimal amount, String email) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.email = email;
    }

    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }
}

// Made with Bob
