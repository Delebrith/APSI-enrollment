export interface Page<T> {
  totalElements: number;
  totalPages: number;
  pageNumber: number;
  pageSize: number;
  items: T[];
}
