import { Enrollment } from './enrollment.model';

export enum PaymentStatus {
  NOT_ENROLLED = 'NEW',
  PENDING = 'SUCCESS',
  ENROLLED = 'CANCELED',
}

export interface Payment {
  id: number;
  orderId: string;
  status: PaymentStatus;
  enrollment: Enrollment;
  redirectUrl: string;
}
