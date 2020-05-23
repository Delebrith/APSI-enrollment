import { Enrollment } from './enrollment.model';

export enum PaymentStatus {
  NOT_ENROLLED = 'NOT_ENROLLED',
  PENDING = 'PENDING',
  ENROLLED = 'ENROLLED',
}

export interface Payment {
  id: number;
  orderId: string;
  status: PaymentStatus;
  enrollment: Enrollment;
  redirectUrl: string;
}
