// TypeScript types matching the Java backend DTOs

export enum OrderStatus {
  PENDING = 'PENDING',
  PROCESSING = 'PROCESSING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface OrderItem {
  productId: string;
  productName: string;
  quantity: number;
  unitPrice: number;
  subtotal: number;
}

export interface Order {
  id: string;
  customerId: string;
  items: OrderItem[];
  orderDate: string; // ISO 8601 format
  deliveryDate: string; // ISO 8601 format
  status: OrderStatus;
  total: number;
}

export interface CreateOrderRequest {
  customerId: string;
  items: {
    productId: string;
    quantity: number;
  }[];
  deliveryDate: string; // ISO 8601 format
}

export interface UpdateOrderStatusRequest {
  status: OrderStatus;
}

// Made with Bob
