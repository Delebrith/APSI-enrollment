import { Organizer } from './organizer.model';

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
  organizer: Organizer;
}

export interface Event {
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
  placeId: number;
  speakerIds: number[];
}
