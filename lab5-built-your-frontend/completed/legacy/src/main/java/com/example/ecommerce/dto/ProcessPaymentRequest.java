package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO for processing payment (Java 21 Record)
 * Base payment request with common fields
 */
public record ProcessPaymentRequest(
    @NotBlank(message = "Payment method is required")
    String paymentMethod,
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    BigDecimal amount,
    
    // Credit Card fields (optional based on payment method)
    String cardNumber,
    String cvv,
    String expiryDate,
    
    // PayPal fields
    String email,
    
    // Bank Transfer fields
    String accountNumber,
    String routingNumber
) {}

// Made with Bob