import { Place } from './place.model';
import { User } from './user.model';

export enum EventType {
  LECTURE = 'LECTURE',
  LABORATORY = 'LABORATORY',
  PROJECT = 'PROJECT',
  SEMINAR = 'SEMINAR',
  WORKSHOP = 'WORKSHOP',
  COURSE = 'COURSE',
  CONFERENCE = 'CONFERENCE',
  MEETING = 'MEETING',
}

export interface BasicEvent {
  id: number;
  name: string;
  description: string;
  eventType: EventType;
  attendeesLimit: number;
  start: Date;
  end: Date;
  organizer: User;
}

export interface EventRequest {
  name: string;
  description: string;
  eventType: EventType;
  attendeesLimit: number;
  meetings: MeetingRequest[];
}

export interface MeetingRequest {
  description: string;
  start: Date;
  end: Date;
  placeId: number;
  speakerIds: number[];
}

export interface Event {
  id: number;
  name: string;
  description: string;
  eventType: EventType;
  attendeesLimit: number;
  meetings: Meeting[];
}

export interface Meeting {
  description: string;
  start: Date;
  end: Date;
  place: Place;
  speakers: User[];
}
