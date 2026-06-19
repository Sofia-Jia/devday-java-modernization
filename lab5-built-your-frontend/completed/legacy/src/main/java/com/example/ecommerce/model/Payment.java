package com.example.ecommerce.model;

import java.math.BigDecimal;

/**
 * Modernized Payment sealed interface (Java 21)
 *
 * Benefits:
 * - Controlled hierarchy (only specified types can implement)
 * - Exhaustive switch expressions
 * - Better domain modeling
 * - Compiler-enforced type safety
 */
public sealed interface Payment
    permits CreditCardPayment, PayPalPayment, BankTransferPayment {
    
    BigDecimal getAmount();
    PaymentStatus getStatus();
    void setStatus(PaymentStatus status);
    String getPaymentMethod();
}

// Made with Bob
