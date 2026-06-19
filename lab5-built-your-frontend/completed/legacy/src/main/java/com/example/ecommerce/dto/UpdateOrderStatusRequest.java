package com.example.ecommerce.dto;

import com.example.ecommerce.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating order status (Java 21 Record)
 */
public record UpdateOrderStatusRequest(
    @NotNull(message = "Status is required")
    OrderStatus status
) {}

// Made with Bob