import { useState } from 'react';
import OrderList from './components/OrderList';
import OrderForm from './components/OrderForm';
import type { Order } from './types';

function App() {
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [refreshTrigger, setRefreshTrigger] = useState(0);

  const handleOrderCreated = (order: Order) => {
    setShowCreateForm(false);
    setRefreshTrigger(prev => prev + 1); // Trigger refresh of order list
    alert(`Order ${order.id} created successfully!`);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div className="flex justify-between items-center">
            <div className="flex items-center space-x-3">
              <svg className="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
              </svg>
              <h1 className="text-3xl font-bold text-gray-900">E-Commerce Order Management</h1>
            </div>
            <div className="flex items-center space-x-4">
              <span className="text-sm text-gray-500">React + Java 21</span>
              <div className="w-2 h-2 bg-green-500 rounded-full" title="Connected"></div>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Toggle Button */}
        <div className="mb-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-gray-900">
            {showCreateForm ? 'Create New Order' : 'Orders'}
          </h2>
          <button
            onClick={() => setShowCreateForm(!showCreateForm)}
            className={`px-6 py-3 rounded-lg font-medium transition-colors ${
              showCreateForm
                ? 'bg-gray-600 text-white hover:bg-gray-700'
                : 'bg-blue-600 text-white hover:bg-blue-700'
            }`}
          >
            {showCreateForm ? '← Back to Orders' : '+ Create Order'}
          </button>
        </div>

        {/* Content Area */}
        {showCreateForm ? (
          <div className="bg-white rounded-lg shadow-md p-6">
            <OrderForm
              onSuccess={handleOrderCreated}
              onCancel={() => setShowCreateForm(false)}
            />
          </div>
        ) : (
          <OrderList key={refreshTrigger} />
        )}
      </main>

      {/* Footer */}
      <footer className="bg-white border-t border-gray-200 mt-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <p className="text-center text-sm text-gray-500">
            Demo: Frontend Integration with Java 21 Backend • Made with Bob
          </p>
        </div>
      </footer>
    </div>
  );
}

export default App;

// Made with Bob
