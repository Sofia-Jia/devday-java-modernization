package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modernized OrderService (Java 21)
 *
 * Improvements:
 * - Uses java.time API (LocalDateTime)
 * - Simplified stream operations with .toList()
 * - Cleaner, more maintainable code
 */
public class OrderService {
    private List<Order> orders;

    public OrderService() {
        this.orders = new ArrayList<>();
    }

    /**
     * Find high value orders (modernized with .toList())
     */
    public List<Order> findHighValueOrders(List<Order> orders) {
        return orders.stream()
            .filter(order -> order.getTotal().compareTo(new BigDecimal("1000")) > 0)
            .toList();
    }

    /**
     * Find orders by status (modernized with .toList())
     */
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orders.stream()
            .filter(order -> order.getStatus() == status)
            .toList();
    }

    /**
     * Find overdue orders (modernized with LocalDateTime)
     */
    public List<Order> findOverdueOrders() {
        return orders.stream()
            .filter(Order::isOverdue)
            .toList();
    }

    /**
     * Calculate total revenue (legacy implementation)
     */
    public BigDecimal calculateTotalRevenue() {
        return orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
            .map(Order::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Find order by ID (legacy implementation - returns null)
     * Should return Optional in modern Java
     */
    public Order findById(String orderId) {
        if (orderId == null) {
            return null;
        }
        
        for (Order order : orders) {
            if (orderId.equals(order.getId())) {
                return order;
            }
        }
        return null;
    }

    /**
     * Create new order (modernized with LocalDateTime)
     */
    public Order createOrder(String customerId, LocalDateTime deliveryDate) {
        String orderId = generateOrderId();
        Order order = new Order(orderId, customerId, LocalDateTime.now(), deliveryDate);
        orders.add(order);
        return order;
    }

    /**
     * Update order status (legacy implementation with verbose checks)
     */
    public boolean updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = findById(orderId);
        if (order == null) {
            return false;
        }
        
        if (!order.getStatus().canTransitionTo(newStatus)) {
            return false;
        }
        
        order.setStatus(newStatus);
        return true;
    }

    /**
     * Cancel order (legacy implementation)
     */
    public boolean cancelOrder(String orderId) {
        Order order = findById(orderId);
        if (order == null) {
            return false;
        }
        
        if (order.getStatus() == OrderStatus.DELIVERED || 
            order.getStatus() == OrderStatus.CANCELLED) {
            return false;
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        return true;
    }

    /**
     * Get orders in date range (modernized with LocalDateTime)
     */
    public List<Order> getOrdersInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return new ArrayList<>();
        }
        
        return orders.stream()
            .filter(order -> {
                LocalDateTime orderDate = order.getOrderDate();
                return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
            })
            .toList();
    }

    /**
     * Get order statistics (legacy implementation)
     */
    public OrderStatistics getStatistics() {
        int totalOrders = orders.size();
        int activeOrders = (int) orders.stream()
            .filter(order -> order.getStatus().isActive())
            .count();
        int completedOrders = (int) orders.stream()
            .filter(order -> order.getStatus().isCompleted())
            .count();
        BigDecimal totalRevenue = calculateTotalRevenue();
        
        return new OrderStatistics(totalOrders, activeOrders, completedOrders, totalRevenue);
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Delete orders with 0 items
     * @return Number of orders deleted
     */
    public int deleteEmptyOrders() {
        int initialSize = orders.size();
        orders.removeIf(order -> order.getItems().isEmpty());
        return initialSize - orders.size();
    }

    /**
     * Delete a specific order by ID
     * @param orderId Order ID to delete
     * @return true if order was deleted, false if not found
     */
    public boolean deleteOrder(String orderId) {
        Order order = findById(orderId);
        if (order == null) {
            return false;
        }
        return orders.remove(order);
    }
}

/**
 * OrderStatistics record (Java 21)
 * Immutable data carrier for order statistics
 */
record OrderStatistics(
    int totalOrders,
    int activeOrders,
    int completedOrders,
    BigDecimal totalRevenue
) {}

// Made with Bob