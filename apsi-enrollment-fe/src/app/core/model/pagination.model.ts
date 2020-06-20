export interface Page<T> {
  totalElements: number;
  totalPages: number;
  pageNumber: number;
  pageSize: number;
  items: T[];
}

export interface PageRequest {
  pageNumber?: number;
  pageSize?: number;
}

export interface PageSearchRequest {
  pageNumber?: number;
  pageSize?: number;
  searchQuery?: string;
}
