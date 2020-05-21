import { Enrollment } from './enrollment.model';

export interface Payment {
    id: number;
    orderId: number;
    status: string;
    enrollment: Enrollment; 
}