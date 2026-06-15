package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.model.*;
import com.example.ecommerce.service.InventoryService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Order Management (Java 21 + Spring Boot)
 * 
 * Provides RESTful endpoints for:
 * - Creating orders
 * - Listing orders
 * - Getting order by ID
 * - Updating order status
 * - Processing payments
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(originPatterns = {"http://localhost:*"}, allowCredentials = "true")
public class OrderController {
    
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    
    public OrderController(OrderService orderService, PaymentService paymentService, InventoryService inventoryService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }
    
    /**
     * GET /api/orders - List all orders
     * 
     * @return List of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> response = orders.stream()
            .map(OrderResponse::from)
            .toList();
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/orders/{id} - Get order by ID
     * 
     * @param id Order ID
     * @return Order details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        Order order = orderService.findById(id);
        
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(OrderResponse.from(order));
    }
    
    /**
     * POST /api/orders - Create a new order
     * 
     * @param request Order creation request with validation
     * @return Created order with 201 status
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        // Create the order
        Order order = orderService.createOrder(request.customerId(), request.deliveryDate());
        
        // Add items to the order
        for (OrderItemRequest itemRequest : request.items()) {
            // Get product details from inventory
            Product product = inventoryService.getProduct(itemRequest.productId());
            
            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
            
            // Check inventory availability
            if (!inventoryService.isInStock(itemRequest.productId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            // Reserve stock
            boolean reserved = inventoryService.reserveStock(
                itemRequest.productId(), 
                itemRequest.quantity()
            );
            
            if (!reserved) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            // Add item to order
            OrderItem orderItem = new OrderItem(
                product.id(),
                product.name(),
                itemRequest.quantity(),
                product.price()
            );
            order.addItem(orderItem);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(OrderResponse.from(order));
    }
    
    /**
     * PUT /api/orders/{id}/status - Update order status
     * 
     * @param id Order ID
     * @param request Status update request
     * @return Updated order or 404/400 if invalid
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        
        boolean updated = orderService.updateOrderStatus(id, request.status());
        
        if (!updated) {
            Order order = orderService.findById(id);
            if (order == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
        
        Order order = orderService.findById(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }
    
    /**
     * POST /api/orders/{id}/payment - Process payment for an order
     * 
     * @param id Order ID
     * @param request Payment processing request
     * @return Payment result or error status
     */
    @PostMapping("/{id}/payment")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable String id,
            @Valid @RequestBody ProcessPaymentRequest request) {
        
        Order order = orderService.findById(id);
        
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Create payment based on payment method
        Payment payment = createPaymentFromRequest(request);
        
        // Validate payment
        if (!paymentService.validatePayment(payment)) {
            return ResponseEntity.badRequest().build();
        }
        
        // Process payment
        String result = paymentService.processPayment(payment);
        payment.setStatus(PaymentStatus.COMPLETED);
        
        // Update order status
        orderService.updateOrderStatus(id, OrderStatus.PROCESSING);
        
        return ResponseEntity.ok(new PaymentResponse(
            payment.getPaymentMethod(),
            payment.getAmount(),
            payment.getStatus(),
            result
        ));
    }
    
    /**
     * Helper method to create Payment object from request
     */
    private Payment createPaymentFromRequest(ProcessPaymentRequest request) {
        return switch (request.paymentMethod().toUpperCase()) {
            case "CREDIT_CARD" -> new CreditCardPayment(
                request.amount(),
                request.cardNumber(),
                request.cvv(),
                request.expiryDate()
            );
            case "PAYPAL" -> new PayPalPayment(
                request.amount(),
                request.email()
            );
            case "BANK_TRANSFER" -> new BankTransferPayment(
                request.amount(),
                request.accountNumber(),
                request.routingNumber()
            );
            default -> throw new IllegalArgumentException("Invalid payment method: " + request.paymentMethod());
        };
    }
    
    /**
     * DELETE /api/orders/{id} - Delete a specific order
     *
     * @param id Order ID to delete
     * @return 204 No Content if deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        boolean deleted = orderService.deleteOrder(id);
        
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * DELETE /api/orders/cleanup/empty - Delete all orders with 0 items
     *
     * @return Number of orders deleted
     */
    @DeleteMapping("/cleanup/empty")
    public ResponseEntity<DeleteEmptyOrdersResponse> deleteEmptyOrders() {
        int deletedCount = orderService.deleteEmptyOrders();
        return ResponseEntity.ok(new DeleteEmptyOrdersResponse(deletedCount));
    }
}

/**
 * Response for delete empty orders operation
 */
record DeleteEmptyOrdersResponse(int deletedCount) {}

// Made with Bob