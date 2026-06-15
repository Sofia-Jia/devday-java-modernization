package com.example.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Test suite for Order class (Modernized Java 21 version)
 * These tests verify the modernized LocalDateTime implementation
 */
public class OrderTest {
    
    private Order order;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    
    @BeforeEach
    public void setUp() {
        // Create test dates using modern java.time API
        orderDate = LocalDateTime.now();
        
        // Delivery date is 3 days from now
        deliveryDate = orderDate.plusDays(3);
        
        // Create test order
        order = new Order("ORD-001", "CUST-001", orderDate, deliveryDate);
    }
    
    @Test
    public void testOrderCreation() {
        assertNotNull(order);
        assertEquals("ORD-001", order.getId());
        assertNotNull(order.getOrderDate());
        assertNotNull(order.getDeliveryDate());
    }
    
    @Test
    public void testIsNotOverdue() {
        // Delivery date is in the future, so should not be overdue
        assertFalse(order.isOverdue(), "Order with future delivery date should not be overdue");
    }
    
    @Test
    public void testIsOverdue() {
        // Create order with past delivery date
        LocalDateTime pastDate = orderDate.minusDays(3); // 3 days ago
        
        Order overdueOrder = new Order("ORD-002", "CUST-001", orderDate, pastDate);
        assertTrue(overdueOrder.isOverdue(), "Order with past delivery date should be overdue");
    }
    
    @Test
    public void testDateImmutability() {
        // LocalDateTime is immutable by default - no defensive copies needed!
        LocalDateTime originalOrderDate = order.getOrderDate();
        LocalDateTime retrievedDate = order.getOrderDate();
        
        // LocalDateTime is immutable, so any "modification" creates a new instance
        LocalDateTime modifiedDate = retrievedDate.plusDays(1);
        
        // The order's internal date should NOT be changed
        assertEquals(originalOrderDate, order.getOrderDate(),
            "LocalDateTime is immutable - internal dates cannot be modified externally");
        assertNotEquals(modifiedDate, order.getOrderDate(),
            "Modified date should be different from order's date");
    }
    
    @Test
    public void testDateComparison() {
        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusDays(1);
        
        assertTrue(date1.isBefore(date2), "Earlier date should be before later date");
        assertTrue(date2.isAfter(date1), "Later date should be after earlier date");
    }
}

// Made with Bob
