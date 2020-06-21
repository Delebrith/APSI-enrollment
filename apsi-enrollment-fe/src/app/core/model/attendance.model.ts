import { Meeting } from './event.model';
import { User } from './user.model';


export enum AttendanceStatus {
  UNCHECKED = 'UNCHECKED',
  PRESENT = 'PRESENT',
  ABSENT = 'ABSENT',
}


export interface Attendance {
  id: number;
  meeting: Meeting;
  user: User;
  status: AttendanceStatus;
}
