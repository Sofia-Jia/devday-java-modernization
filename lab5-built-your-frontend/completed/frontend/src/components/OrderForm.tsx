// OrderForm component - Create new orders with validation

import { useState } from 'react';
import { useForm, useFieldArray } from 'react-hook-form';
import api from '../services/api';
import type { CreateOrderRequest, Order } from '../types/order.types';
import type { ApiError } from '../types/api.types';

// Form data interface
interface OrderFormData {
  customerId: string;
  items: Array<{
    productId: string;
    quantity: number;
  }>;
  deliveryDate: string;
}

// Mock product data (in real app, fetch from API)
// Product IDs must match backend inventory (P001, P002, etc.)
const MOCK_PRODUCTS = [
  { id: 'P001', name: 'Laptop', price: 999.99 },
  { id: 'P002', name: 'Mouse', price: 29.99 },
  { id: 'P003', name: 'Keyboard', price: 79.99 },
  { id: 'P004', name: 'Monitor', price: 299.99 },
  { id: 'P005', name: 'Headphones', price: 149.99 },
];

interface OrderFormProps {
  onSuccess?: (order: Order) => void;
  onCancel?: () => void;
}

export default function OrderForm({ onSuccess, onCancel }: OrderFormProps) {
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);
  const [success, setSuccess] = useState(false);

  const {
    register,
    control,
    handleSubmit,
    watch,
    reset,
    formState: { errors },
  } = useForm<OrderFormData>({
    defaultValues: {
      customerId: '',
      items: [{ productId: '', quantity: 1 }],
      deliveryDate: '',
    },
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'items',
  });

  const watchItems = watch('items');

  // Calculate total price
  const calculateTotal = (): number => {
    return watchItems.reduce((total: number, item: { productId: string; quantity: number }) => {
      const product = MOCK_PRODUCTS.find((p) => p.id === item.productId);
      if (product && item.quantity > 0) {
        return total + product.price * item.quantity;
      }
      return total;
    }, 0);
  };

  const onSubmit = async (data: OrderFormData) => {
    try {
      setSubmitting(true);
      setError(null);
      setSuccess(false);

      // Filter out empty items
      const validItems = data.items.filter(
        (item) => item.productId && item.productId.trim() !== '' && item.quantity > 0
      );

      if (validItems.length === 0) {
        setError({
          message: 'Please select a product and enter quantity for at least one item',
          status: 400,
        });
        setSubmitting(false);
        return;
      }

      const orderRequest: CreateOrderRequest = {
        customerId: data.customerId,
        items: validItems,
        deliveryDate: `${data.deliveryDate}T10:00:00`, // Add time component for LocalDateTime
      };

      console.log('Sending order request:', JSON.stringify(orderRequest, null, 2));
      const createdOrder = await api.createOrder(orderRequest);
      
      setSuccess(true);
      reset();
      
      if (onSuccess) {
        onSuccess(createdOrder);
      }

      // Auto-hide success message after 3 seconds
      setTimeout(() => setSuccess(false), 3000);
    } catch (err) {
      setError(err as ApiError);
    } finally {
      setSubmitting(false);
    }
  };

  // Get minimum date (tomorrow)
  const getMinDate = () => {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    return tomorrow.toISOString().split('T')[0];
  };

  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="bg-white rounded-lg shadow-lg p-8">
        {/* Header */}
        <div className="mb-6">
          <h2 className="text-2xl font-bold text-gray-900">Create New Order</h2>
          <p className="mt-2 text-gray-600">
            Fill in the details below to create a new order
          </p>
        </div>

        {/* Success Message */}
        {success && (
          <div className="mb-6 bg-green-50 border border-green-200 rounded-lg p-4">
            <div className="flex items-center">
              <svg className="w-5 h-5 text-green-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              <p className="text-green-800 font-medium">Order created successfully!</p>
            </div>
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
            <div className="flex items-center">
              <svg className="w-5 h-5 text-red-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <p className="text-red-800">{error.message}</p>
            </div>
          </div>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          {/* Customer ID */}
          <div>
            <label htmlFor="customerId" className="block text-sm font-medium text-gray-700 mb-2">
              Customer ID *
            </label>
            <input
              id="customerId"
              type="text"
              {...register('customerId', {
                required: 'Customer ID is required',
                pattern: {
                  value: /^CUST-\d{3}$/,
                  message: 'Customer ID must be in format CUST-XXX (e.g., CUST-001)',
                },
              })}
              className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent ${
                errors.customerId ? 'border-red-500' : 'border-gray-300'
              }`}
              placeholder="CUST-001"
            />
            {errors.customerId && (
              <p className="mt-1 text-sm text-red-600">{errors.customerId.message}</p>
            )}
          </div>

          {/* Delivery Date */}
          <div>
            <label htmlFor="deliveryDate" className="block text-sm font-medium text-gray-700 mb-2">
              Delivery Date *
            </label>
            <input
              id="deliveryDate"
              type="date"
              min={getMinDate()}
              {...register('deliveryDate', {
                required: 'Delivery date is required',
              })}
              className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent ${
                errors.deliveryDate ? 'border-red-500' : 'border-gray-300'
              }`}
            />
            {errors.deliveryDate && (
              <p className="mt-1 text-sm text-red-600">{errors.deliveryDate.message}</p>
            )}
          </div>

          {/* Order Items */}
          <div>
            <div className="flex items-center justify-between mb-4">
              <label className="block text-sm font-medium text-gray-700">
                Order Items *
              </label>
              <button
                type="button"
                onClick={() => append({ productId: '', quantity: 1 })}
                className="flex items-center gap-2 text-sm text-blue-600 hover:text-blue-700 font-medium"
              >
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                </svg>
                Add Item
              </button>
            </div>

            <div className="space-y-4">
              {fields.map((field: any, index: number) => (
                <div key={field.id} className="flex gap-4 items-start p-4 bg-gray-50 rounded-lg">
                  <div className="flex-1">
                    <label className="block text-xs font-medium text-gray-700 mb-1">
                      Product
                    </label>
                    <select
                      {...register(`items.${index}.productId`, {
                        required: 'Product is required',
                      })}
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent ${
                        errors.items?.[index]?.productId ? 'border-red-500' : 'border-gray-300'
                      }`}
                    >
                      <option value="">Select a product</option>
                      {MOCK_PRODUCTS.map((product) => (
                        <option key={product.id} value={product.id}>
                          {product.name} - ${product.price.toFixed(2)}
                        </option>
                      ))}
                    </select>
                    {errors.items?.[index]?.productId && (
                      <p className="mt-1 text-xs text-red-600">
                        {errors.items[index]?.productId?.message}
                      </p>
                    )}
                  </div>

                  <div className="w-32">
                    <label className="block text-xs font-medium text-gray-700 mb-1">
                      Quantity
                    </label>
                    <input
                      type="number"
                      min="1"
                      {...register(`items.${index}.quantity`, {
                        required: 'Quantity is required',
                        min: { value: 1, message: 'Minimum quantity is 1' },
                        max: { value: 100, message: 'Maximum quantity is 100' },
                      })}
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent ${
                        errors.items?.[index]?.quantity ? 'border-red-500' : 'border-gray-300'
                      }`}
                    />
                    {errors.items?.[index]?.quantity && (
                      <p className="mt-1 text-xs text-red-600">
                        {errors.items[index]?.quantity?.message}
                      </p>
                    )}
                  </div>

                  {fields.length > 1 && (
                    <button
                      type="button"
                      onClick={() => remove(index)}
                      className="mt-6 p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                      title="Remove item"
                    >
                      <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  )}
                </div>
              ))}
            </div>
          </div>

          {/* Total Price */}
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
            <div className="flex justify-between items-center">
              <span className="text-lg font-medium text-gray-700">Estimated Total:</span>
              <span className="text-2xl font-bold text-blue-600">
                ${calculateTotal().toFixed(2)}
              </span>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex gap-4 pt-4">
            <button
              type="submit"
              disabled={submitting}
              className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-400 text-white font-medium py-3 px-6 rounded-lg transition-colors duration-200 flex items-center justify-center gap-2"
            >
              {submitting ? (
                <>
                  <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                  Creating Order...
                </>
              ) : (
                <>
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                  </svg>
                  Create Order
                </>
              )}
            </button>

            {onCancel && (
              <button
                type="button"
                onClick={onCancel}
                disabled={submitting}
                className="px-6 py-3 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 font-medium transition-colors duration-200"
              >
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}

// Made with Bob
