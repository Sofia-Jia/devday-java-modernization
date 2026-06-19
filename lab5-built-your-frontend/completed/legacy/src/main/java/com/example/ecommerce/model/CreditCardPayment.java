package com.example.ecommerce.model;

import java.math.BigDecimal;

public final class CreditCardPayment implements Payment {
    private final BigDecimal amount;
    private PaymentStatus status;
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;

    public CreditCardPayment(BigDecimal amount, String cardNumber, String cvv, String expiryDate) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
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

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}

// Made with Bob
