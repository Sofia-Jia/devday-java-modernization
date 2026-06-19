// TypeScript types for payment-related operations

export enum PaymentStatus {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED'
}

export interface ProcessPaymentRequest {
  orderId: string;
  paymentMethod: 'CREDIT_CARD' | 'PAYPAL' | 'BANK_TRANSFER';
  amount: number;
  // Credit card fields
  cardNumber?: string;
  cvv?: string;
  expiryDate?: string;
  // PayPal fields
  email?: string;
  // Bank transfer fields
  accountNumber?: string;
  routingNumber?: string;
}

export interface PaymentResponse {
  paymentMethod: string;
  amount: number;
  status: PaymentStatus;
  transactionId: string;
}

// Made with Bob