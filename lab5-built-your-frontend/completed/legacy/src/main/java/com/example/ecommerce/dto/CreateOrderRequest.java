package com.example.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for creating a new order (Java 21 Record)
 * Uses Jakarta validation annotations for input validation
 */
public record CreateOrderRequest(
    @NotBlank(message = "Customer ID is required")
    String customerId,
    
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    List<OrderItemRequest> items,
    
    @NotNull(message = "Delivery date is required")
    LocalDateTime deliveryDate
) {}

// Made with Bob