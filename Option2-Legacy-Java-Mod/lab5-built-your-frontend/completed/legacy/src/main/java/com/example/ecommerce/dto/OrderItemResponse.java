package com.example.ecommerce.dto;

import java.math.BigDecimal;

/**
 * DTO for order item in response (Java 21 Record)
 */
public record OrderItemResponse(
    String productId,
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {
    /**
     * Factory method to create from OrderItem
     */
    public static OrderItemResponse from(com.example.ecommerce.model.OrderItem item) {
        return new OrderItemResponse(
            item.productId(),
            item.productName(),
            item.quantity(),
            item.unitPrice(),
            item.getSubtotal()
        );
    }
}

// Made with Bob