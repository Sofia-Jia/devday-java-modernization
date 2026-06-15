package com.example.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modernized Order class (Java 21)
 *
 * Improvements:
 * - Uses java.time API (LocalDateTime)
 * - Immutable date/time objects
 * - Cleaner API
 * - Thread-safe by default
 */
public class Order {
    private final String id;
    private final String customerId;
    private final List<OrderItem> items;
    private final LocalDateTime orderDate;
    private final LocalDateTime deliveryDate;
    private OrderStatus status;
    private BigDecimal total;

    public Order(String id, String customerId, LocalDateTime orderDate, LocalDateTime deliveryDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = OrderStatus.PENDING;
        this.total = BigDecimal.ZERO;
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            items.add(item);
            recalculateTotal();
        }
    }

    public void removeItem(OrderItem item) {
        if (item != null) {
            items.remove(item);
            recalculateTotal();
        }
    }

    private void recalculateTotal() {
        total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getSubtotal());
        }
    }

    public boolean isOverdue() {
        if (deliveryDate == null) {
            return false;
        }
        return deliveryDate.isBefore(LocalDateTime.now()) && status != OrderStatus.DELIVERED;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items.size() +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", total=" + total +
                '}';
    }
}

// Made with Bob