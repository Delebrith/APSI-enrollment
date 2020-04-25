export interface Page<T> {
  totalPages: number;
  pageNumber: number;
  pageSize: number;
  items: T[];
}
