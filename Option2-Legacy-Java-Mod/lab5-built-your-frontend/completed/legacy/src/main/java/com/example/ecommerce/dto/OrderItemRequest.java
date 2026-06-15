package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for order item in create request (Java 21 Record)
 */
public record OrderItemRequest(
    @NotBlank(message = "Product ID is required")
    String productId,
    
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity
) {}

// Made with Bob