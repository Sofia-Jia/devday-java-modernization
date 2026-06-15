package com.example.ecommerce.config;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.InventoryService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Spring Configuration for Service Beans
 * Provides singleton instances of services with sample data
 */
@Configuration
public class ServiceConfiguration {
    
    @Bean
    public OrderService orderService() {
        return new OrderService();
    }
    
    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
    
    @Bean
    public InventoryService inventoryService() {
        InventoryService service = new InventoryService();
        
        // Add sample products for testing
        service.addProduct(
            new Product("P001", "Laptop", new BigDecimal("999.99"), "Electronics", "High-performance laptop", 10),
            10
        );
        
        service.addProduct(
            new Product("P002", "Mouse", new BigDecimal("29.99"), "Electronics", "Wireless mouse", 50),
            50
        );
        
        service.addProduct(
            new Product("P003", "Keyboard", new BigDecimal("79.99"), "Electronics", "Mechanical keyboard", 30),
            30
        );
        
        service.addProduct(
            new Product("P004", "Monitor", new BigDecimal("299.99"), "Electronics", "27-inch 4K monitor", 15),
            15
        );
        
        service.addProduct(
            new Product("P005", "Headphones", new BigDecimal("149.99"), "Electronics", "Noise-cancelling headphones", 25),
            25
        );
        
        return service;
    }
}

// Made with Bob