import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Enrollment, EnrollmentStatus } from 'src/app/core/model/enrollment.model';
import { BasicEvent, Event, EventRequest, MeetingRequest } from 'src/app/core/model/event.model';
import { Page, PageSearchRequest } from 'src/app/core/model/pagination.model';
import { UserRole } from 'src/app/core/model/user.model';
import { environment } from 'src/environments/environment';
import {Attendance} from '../../../../core/model/attendance.model';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private eventBaseUrl = `${environment.apiBaseUrl}/event`;
  private enrollmentBaseUrl = `${environment.apiBaseUrl}/enrollment`;

  constructor(private http: HttpClient) {}

  getEventsPage(request: PageSearchRequest): Observable<Page<BasicEvent>> {
    return this.localGetEventsPage(request, this.eventBaseUrl);
  }

  getMyEventsBySpeakerPage(request: PageSearchRequest): Observable<Page<BasicEvent>> {
    return this.localGetEventsPage(request, `${this.eventBaseUrl}/my-speaker`);
  }

  getMyEventsByOrganizerPage(request: PageSearchRequest): Observable<Page<BasicEvent>> {
    return this.localGetEventsPage(request, `${this.eventBaseUrl}/my-organizer`);
  }

  getMyEnrolledEventsPage(request: PageSearchRequest): Observable<Page<BasicEvent>> {
    return this.localGetEventsPage(request, `${this.eventBaseUrl}/my-enrolled`);
  }

  private localGetEventsPage(
    { pageNumber, pageSize, searchQuery }: PageSearchRequest,
    address: string
  ): Observable<Page<BasicEvent>> {
    const params = {
      page: null,
      size: null,
      searchQuery: null,
    };
    if (pageNumber != null) {
      params.page = pageNumber;
    } else {
      delete params.page;
    }
    if (pageSize != null) {
      params.size = pageSize;
    } else {
      delete params.size;
    }
    if (searchQuery != null) {
      params.searchQuery = searchQuery;
    } else {
      delete params.searchQuery;
    }

    return this.http
      .get<any>(address, { params })
      .pipe(
        map(({ content, number: retrievedPageNumber, totalPages, size, totalElements }) => {
          return {
            items: content,
            pageNumber: retrievedPageNumber,
            pageSize: size,
            totalPages,
            totalElements,
          } as Page<BasicEvent>;
        })
      );
  }

  getEventById(eventId: number): Observable<Event> {
    return this.http.get<any>(`${this.eventBaseUrl}/${eventId}`).pipe(
      map(({ event, meetings }) => {
        const { id, name, description, eventType, attendeesLimit, organizer, cost } = event;
        return {
          id,
          name,
          description,
          eventType,
          attendeesLimit,
          organizer,
          meetings,
          cost,
        } as Event;
      })
    );
  }

  getAllowedToCreate(): Observable<{ EventType: UserRole[] }> {
    return this.http
      .get<any>(`${this.eventBaseUrl}/allowed-to-create`)
      .pipe(map(({ allowedToCreate }) => allowedToCreate as { EventType: UserRole[] }));
  }

  createNewEvent(event: EventRequest): Observable<BasicEvent> {
    const postBody = {
      ...event,
      meetings: event.meetings.map((meeting: MeetingRequest) => ({
        ...meeting,
        start: meeting.start.toISOString(),
        end: meeting.end.toISOString(),
      })),
    };
    return this.http.post(`${this.eventBaseUrl}`, postBody).pipe(map((response) => response as BasicEvent));
  }

  getEnrollment(eventId: number): Observable<Enrollment> {
    return this.http
      .get<any>(`${this.enrollmentBaseUrl}/my-enrollments`)
      .pipe(map((enrollments: Enrollment[]) => enrollments.find((e) => e.event.id === eventId)));
  }

  getEnrollmentStatus(eventId: number): Observable<EnrollmentStatus> {
    return this.getEnrollment(eventId).pipe(
      map((enrollment: Enrollment) => enrollment?.status ?? EnrollmentStatus.NOT_ENROLLED)
    );
  }

  signUp(eventId: number): Observable<Enrollment> {
    const postBody = {
      eventId,
    };
    return this.http.post(`${this.enrollmentBaseUrl}`, postBody).pipe(map((response) => response as Enrollment));
  }
}
