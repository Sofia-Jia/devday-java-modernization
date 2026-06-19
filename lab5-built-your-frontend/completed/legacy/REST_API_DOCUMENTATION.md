# E-Commerce Order Management REST API

## Overview

Spring Boot REST API for managing e-commerce orders with support for multiple payment methods.

**Base URL:** `http://localhost:8080/api`

## Endpoints

### 1. List All Orders

**GET** `/api/orders`

Returns a list of all orders in the system.

**Response:** `200 OK`
```json
[
  {
    "id": "ORD-1234567890",
    "customerId": "C001",
    "items": [
      {
        "productId": "P001",
        "productName": "Laptop",
        "quantity": 1,
        "unitPrice": 999.99,
        "subtotal": 999.99
      }
    ],
    "orderDate": "2024-03-28T18:00:00",
    "deliveryDate": "2024-04-05T18:00:00",
    "status": "PENDING",
    "total": 999.99
  }
]
```

---

### 2. Get Order by ID

**GET** `/api/orders/{id}`

Retrieves a specific order by its ID.

**Parameters:**
- `id` (path) - Order ID

**Response:** `200 OK` or `404 Not Found`
```json
{
  "id": "ORD-1234567890",
  "customerId": "C001",
  "items": [...],
  "orderDate": "2024-03-28T18:00:00",
  "deliveryDate": "2024-04-05T18:00:00",
  "status": "PENDING",
  "total": 999.99
}
```

---

### 3. Create Order

**POST** `/api/orders`

Creates a new order with the specified items.

**Request Body:**
```json
{
  "customerId": "C001",
  "items": [
    {
      "productId": "P001",
      "quantity": 2
    },
    {
      "productId": "P002",
      "quantity": 1
    }
  ],
  "deliveryDate": "2024-04-05T18:00:00"
}
```

**Validation Rules:**
- `customerId`: Required, not blank
- `items`: Required, at least one item
- `items[].productId`: Required, not blank
- `items[].quantity`: Required, minimum 1
- `deliveryDate`: Required

**Response:** `201 Created` or `400 Bad Request` or `409 Conflict` (out of stock)
```json
{
  "id": "ORD-1234567890",
  "customerId": "C001",
  "items": [...],
  "orderDate": "2024-03-28T18:00:00",
  "deliveryDate": "2024-04-05T18:00:00",
  "status": "PENDING",
  "total": 2029.97
}
```

---

### 4. Update Order Status

**PUT** `/api/orders/{id}/status`

Updates the status of an existing order.

**Parameters:**
- `id` (path) - Order ID

**Request Body:**
```json
{
  "status": "PROCESSING"
}
```

**Valid Status Values:**
- `PENDING`
- `PROCESSING`
- `SHIPPED`
- `DELIVERED`
- `CANCELLED`

**Response:** `200 OK` or `404 Not Found` or `400 Bad Request`
```json
{
  "id": "ORD-1234567890",
  "customerId": "C001",
  "items": [...],
  "orderDate": "2024-03-28T18:00:00",
  "deliveryDate": "2024-04-05T18:00:00",
  "status": "PROCESSING",
  "total": 2029.97
}
```

---

### 5. Process Payment

**POST** `/api/orders/{id}/payment`

Processes payment for an order using the specified payment method.

**Parameters:**
- `id` (path) - Order ID

**Request Body (Credit Card):**
```json
{
  "paymentMethod": "CREDIT_CARD",
  "amount": 2029.97,
  "cardNumber": "4111111111111111",
  "cvv": "123",
  "expiryDate": "12/25"
}
```

**Request Body (PayPal):**
```json
{
  "paymentMethod": "PAYPAL",
  "amount": 2029.97,
  "email": "customer@example.com"
}
```

**Request Body (Bank Transfer):**
```json
{
  "paymentMethod": "BANK_TRANSFER",
  "amount": 2029.97,
  "accountNumber": "123456789",
  "routingNumber": "987654321"
}
```

**Validation Rules:**
- `paymentMethod`: Required, not blank
- `amount`: Required, must be positive
- Payment method specific fields required based on method

**Response:** `200 OK` or `404 Not Found` or `400 Bad Request`
```json
{
  "paymentMethod": "Credit Card",
  "amount": 2029.97,
  "status": "COMPLETED",
  "message": "Processing Credit Card payment of 2029.97 (fee: 58.87, total: 2088.84)"
}
```

---

## Error Responses

### Validation Error (400 Bad Request)
```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2024-03-28T18:00:00",
  "errors": {
    "customerId": "Customer ID is required",
    "items": "Order must contain at least one item"
  }
}
```

### Not Found (404)
```json
{
  "status": 404,
  "message": "Order not found",
  "timestamp": "2024-03-28T18:00:00",
  "errors": null
}
```

### Server Error (500)
```json
{
  "status": 500,
  "message": "An unexpected error occurred",
  "timestamp": "2024-03-28T18:00:00",
  "errors": null
}
```

---

## CORS Configuration

The API is configured to accept requests from:
- `http://localhost:3000` (React default)
- `http://localhost:5173` (Vite default)

Allowed methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`

---

## Sample Products

The following products are pre-loaded for testing:

| ID | Name | Price | Stock |
|----|------|-------|-------|
| P001 | Laptop | $999.99 | 10 |
| P002 | Mouse | $29.99 | 50 |
| P003 | Keyboard | $79.99 | 30 |
| P004 | Monitor | $299.99 | 15 |
| P005 | Headphones | $149.99 | 25 |

---

## Running the Application

```bash
cd lab5-frontend-integration/legacy
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

---

## Testing with cURL

### Create an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "C001",
    "items": [
      {"productId": "P001", "quantity": 1},
      {"productId": "P002", "quantity": 2}
    ],
    "deliveryDate": "2024-04-05T18:00:00"
  }'
```

### List Orders
```bash
curl http://localhost:8080/api/orders
```

### Process Payment
```bash
curl -X POST http://localhost:8080/api/orders/ORD-1234567890/payment \
  -H "Content-Type: application/json" \
  -d '{
    "paymentMethod": "CREDIT_CARD",
    "amount": 1059.97,
    "cardNumber": "4111111111111111",
    "cvv": "123",
    "expiryDate": "12/25"
  }'
```

---

**Made with Bob** ✨