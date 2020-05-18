import { Event } from './event.model';
import { User } from './user.model';

export enum EnrollmentStatus {
  NOT_ENROLLED = 'NOT_ENROLLED',
  PENDING = 'PENDING',
  ENROLLED = 'ENROLLED',
}

export interface Enrollment {
  id: number;
  user: User;
  event: Event;
  status: EnrollmentStatus;
}
