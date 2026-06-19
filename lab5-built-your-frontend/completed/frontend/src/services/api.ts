// Unified API service layer with Axios configuration and typed endpoints

import axios, { AxiosError } from 'axios';
import type { AxiosInstance, InternalAxiosRequestConfig } from 'axios';
import type {
  Order,
  CreateOrderRequest,
  UpdateOrderStatusRequest,
  OrderStatus,
  ProcessPaymentRequest,
  PaymentResponse,
  ApiError,
} from '../types';

// Create axios instance with base configuration
const apiClient: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 30000, // 30 seconds
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor for adding auth tokens and logging
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // Add authentication token if available
    const token = localStorage.getItem('authToken');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // Add request ID for tracking
    const requestId = `req-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
    if (config.headers) {
      config.headers['X-Request-ID'] = requestId;
    }

    // Log request in development
    if (import.meta.env.DEV) {
      console.log(`[API Request] ${config.method?.toUpperCase()} ${config.url}`, {
        requestId,
        data: config.data,
        params: config.params,
      });
    }

    return config;
  },
  (error) => {
    console.error('[API Request Error]', error);
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
apiClient.interceptors.response.use(
  (response) => {
    // Log response in development
    if (import.meta.env.DEV) {
      console.log(`[API Response] ${response.config.method?.toUpperCase()} ${response.config.url}`, {
        status: response.status,
        data: response.data,
      });
    }
    return response;
  },
  (error: AxiosError) => {
    const apiError: ApiError = {
      message: 'An unexpected error occurred',
      status: error.response?.status || 500,
    };

    if (error.response) {
      // Server responded with error status
      const data = error.response.data as any;
      
      apiError.message = data.message || error.message;
      apiError.status = error.response.status;
      apiError.timestamp = data.timestamp;
      apiError.path = data.path;
      apiError.errors = data.errors;

      // Handle specific error cases
      switch (error.response.status) {
        case 401:
          // Unauthorized - clear token and redirect to login
          localStorage.removeItem('authToken');
          apiError.message = 'Session expired. Please login again.';
          break;
        case 403:
          apiError.message = 'You do not have permission to perform this action.';
          break;
        case 404:
          apiError.message = data.message || 'Resource not found.';
          break;
        case 422:
          apiError.message = 'Validation failed. Please check your input.';
          break;
        case 500:
          apiError.message = 'Server error. Please try again later.';
          break;
        case 503:
          apiError.message = 'Service temporarily unavailable. Please try again later.';
          break;
      }
    } else if (error.request) {
      // Request made but no response received
      apiError.message = 'Network error. Please check your connection.';
      apiError.status = 0;
    }

    // Log error in development
    if (import.meta.env.DEV) {
      console.error('[API Error]', {
        url: error.config?.url,
        method: error.config?.method,
        error: apiError,
      });
    }

    return Promise.reject(apiError);
  }
);

// API Service with typed functions
export const api = {
  /**
   * Create a new order
   * @param order - Order creation request
   * @returns Promise with created order
   */
  async createOrder(order: CreateOrderRequest): Promise<Order> {
    const response = await apiClient.post<Order>('/orders', order);
    return response.data;
  },

  /**
   * Get all orders
   * @returns Promise with array of orders
   */
  async getOrders(): Promise<Order[]> {
    const response = await apiClient.get<Order[]>('/orders');
    return response.data;
  },

  /**
   * Get order by ID
   * @param id - Order ID
   * @returns Promise with order details
   */
  async getOrder(id: string): Promise<Order> {
    const response = await apiClient.get<Order>(`/orders/${id}`);
    return response.data;
  },

  /**
   * Update order status
   * @param id - Order ID
   * @param status - New order status
   * @returns Promise with updated order
   */
  async updateOrderStatus(id: string, status: OrderStatus): Promise<Order> {
    const request: UpdateOrderStatusRequest = { status };
    const response = await apiClient.put<Order>(`/orders/${id}/status`, request);
    return response.data;
  },

  /**
   * Process payment for an order
   * @param orderId - Order ID
   * @param payment - Payment request details
   * @returns Promise with payment response
   */
  async processPayment(orderId: string, payment: ProcessPaymentRequest): Promise<PaymentResponse> {
    const paymentRequest = {
      ...payment,
      orderId,
    };
    const response = await apiClient.post<PaymentResponse>('/orders/payment', paymentRequest);
    return response.data;
  },

  /**
   * Cancel an order
   * @param id - Order ID
   * @returns Promise with cancelled order
   */
  async cancelOrder(id: string): Promise<Order> {
    const response = await apiClient.delete<Order>(`/orders/${id}`);
    return response.data;
  },

  /**
   * Check inventory availability
   * @param productId - Product ID
   * @param quantity - Requested quantity
   * @returns Promise with availability status
   */
  async checkInventory(productId: string, quantity: number): Promise<boolean> {
    const response = await apiClient.post<{ available: boolean }>('/inventory/check', {
      productId,
      quantity,
    });
    return response.data.available;
  },
};

// Export the axios instance for advanced use cases
export { apiClient };

// Export default
export default api;

// Made with Bob
