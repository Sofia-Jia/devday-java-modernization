package com.example.ecommerce.model;

import java.math.BigDecimal;

public final class BankTransferPayment implements Payment {
    private final BigDecimal amount;
    private PaymentStatus status;
    private final String accountNumber;
    private final String routingNumber;

    public BankTransferPayment(BigDecimal amount, String accountNumber, String routingNumber) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }
}

// Made with Bob
