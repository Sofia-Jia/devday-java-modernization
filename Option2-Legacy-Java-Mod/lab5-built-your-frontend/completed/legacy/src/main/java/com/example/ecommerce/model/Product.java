package com.example.ecommerce.model;

import java.math.BigDecimal;

/**
 * Modernized Product record (Java 21)
 *
 * Benefits:
 * - Concise: 1 line vs 80+ lines
 * - Immutable by default
 * - Auto-generated equals, hashCode, toString
 * - Clear intent as a data carrier
 */
public record Product(
    String id,
    String name,
    BigDecimal price,
    String category,
    String description,
    int stockQuantity
) {}

// Made with Bob
