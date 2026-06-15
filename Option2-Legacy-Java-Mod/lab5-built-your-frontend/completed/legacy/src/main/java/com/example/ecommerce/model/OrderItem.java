package com.example.ecommerce.model;

import java.math.BigDecimal;

/**
 * OrderItem record (Java 21)
 * Immutable data carrier for order line items
 */
public record OrderItem(
    String productId,
    String productName,
    int quantity,
    BigDecimal unitPrice
) {
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}

// Made with Bob