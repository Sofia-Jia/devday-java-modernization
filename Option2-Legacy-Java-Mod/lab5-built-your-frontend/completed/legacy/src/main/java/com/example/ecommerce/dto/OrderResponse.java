package com.example.ecommerce.dto;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for order response (Java 21 Record)
 * Converts Order entity to API response format
 */
public record OrderResponse(
    String id,
    String customerId,
    List<OrderItemResponse> items,
    LocalDateTime orderDate,
    LocalDateTime deliveryDate,
    OrderStatus status,
    BigDecimal total
) {
    /**
     * Factory method to create OrderResponse from Order entity
     */
    public static OrderResponse from(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(OrderItemResponse::from)
            .toList();
            
        return new OrderResponse(
            order.getId(),
            order.getCustomerId(),
            itemResponses,
            order.getOrderDate(),
            order.getDeliveryDate(),
            order.getStatus(),
            order.getTotal()
        );
    }
}

// Made with Bob