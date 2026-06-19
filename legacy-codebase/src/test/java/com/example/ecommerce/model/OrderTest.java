package com.example.ecommerce.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Calendar;

/**
 * Test suite for Order class (Legacy Java 8 version)
 * These tests verify the current Date/Calendar implementation
 */
public class OrderTest {
    
    private Order order;
    private Date orderDate;
    private Date deliveryDate;
    
    @BeforeEach
    public void setUp() {
        // Create test dates
        Calendar cal = Calendar.getInstance();
        orderDate = cal.getTime();
        
        // Delivery date is 3 days from now
        cal.add(Calendar.DAY_OF_MONTH, 3);
        deliveryDate = cal.getTime();
        
        // Create test order
        order = new Order("ORD-001", orderDate, deliveryDate);
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
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3); // 3 days ago
        Date pastDate = cal.getTime();
        
        Order overdueOrder = new Order("ORD-002", orderDate, pastDate);
        assertTrue(overdueOrder.isOverdue(), "Order with past delivery date should be overdue");
    }
    
    @Test
    public void testDateImmutability() {
        // This test demonstrates the mutability problem with Date
        Date originalOrderDate = order.getOrderDate();
        Date retrievedDate = order.getOrderDate();
        
        // Modify the retrieved date
        retrievedDate.setTime(retrievedDate.getTime() + 86400000); // Add 1 day
        
        // WARNING: This test will FAIL because Date is mutable!
        // The order's internal date has been changed!
        // This is a critical bug that java.time API fixes
        assertNotEquals(originalOrderDate.getTime(), order.getOrderDate().getTime(),
            "Date mutability allows external modification - this is a bug!");
    }
    
    @Test
    public void testDateComparison() {
        Calendar cal1 = Calendar.getInstance();
        Date date1 = cal1.getTime();
        
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH, 1);
        Date date2 = cal2.getTime();
        
        assertTrue(date1.before(date2), "Earlier date should be before later date");
        assertTrue(date2.after(date1), "Later date should be after earlier date");
    }
}

// Made with Bob
