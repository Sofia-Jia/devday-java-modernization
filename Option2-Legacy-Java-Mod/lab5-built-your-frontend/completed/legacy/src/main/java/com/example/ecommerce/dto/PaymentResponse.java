package com.example.ecommerce.dto;

import com.example.ecommerce.model.PaymentStatus;
import java.math.BigDecimal;

/**
 * DTO for payment response (Java 21 Record)
 */
public record PaymentResponse(
    String paymentMethod,
    BigDecimal amount,
    PaymentStatus status,
    String message
) {}

// Made with Bob