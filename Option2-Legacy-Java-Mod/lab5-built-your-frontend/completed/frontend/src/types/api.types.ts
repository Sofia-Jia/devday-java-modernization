// TypeScript types for API error handling

export interface ApiError {
  message: string;
  status: number;
  timestamp?: string;
  path?: string;
  errors?: Record<string, string>;
}

// Made with Bob