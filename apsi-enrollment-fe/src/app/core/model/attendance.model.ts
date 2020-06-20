import { User } from './user.model';
import { Meeting } from './event.model';


export enum AttendaceStatus {
    UNCHECKED = 'UNCHECKED',
    PRESENT = 'UNCHECKED',
    ABSENT = 'ABSENT',
}


export interface Attendance {
    id: number;
    meeting: Meeting;
    user: User;
    status: AttendaceStatus;
  }
  