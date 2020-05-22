import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Enrollment, EnrollmentStatus } from 'src/app/core/model/enrollment.model';
import { BasicEvent, Event, EventRequest, EventType, MeetingRequest } from 'src/app/core/model/event.model';
import { UserRole } from 'src/app/core/model/user.model';
import { Page } from 'src/app/core/model/pagination.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private eventBaseUrl = `${environment.apiBaseUrl}/event`;
  private enrollmentBaseUrl = `${environment.apiBaseUrl}/enrollment`;

  constructor(private http: HttpClient) {}

  getEventsPage({
    pageNumber,
    pageSize,
    searchQuery,
  }: {
    pageNumber?: number;
    pageSize?: number;
    searchQuery?: string;
  }): Observable<Page<BasicEvent>> {
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
      .get<any>(this.eventBaseUrl, { params })
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
      tap(console.log),
      map(({ event, meetings }) => {
        const { id, name, description, eventType, attendeesLimit } = event;
        return {
          id,
          name,
          description,
          eventType,
          attendeesLimit,
          meetings,
        } as Event;
      })
    );
  }

  getAllowedToCreate(): Observable<{ EventType: UserRole[] }> {
    return this.http.get<any>(`${this.eventBaseUrl}/allowed-to-create`).pipe(
      map(({ allowedToCreate }) => {
        return allowedToCreate as { EventType: UserRole[] };
      })
    );
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

  getEnrollmentStatus(eventId: number): Observable<EnrollmentStatus> {
    return this.http.get<any>(`${this.enrollmentBaseUrl}/my-enrollments`).pipe(
      map((enrollments: Enrollment[]) => enrollments.filter((e) => e.event.id === eventId)),
      map((enrollments: Enrollment[]) => {
        if (enrollments.length === 0) {
          return EnrollmentStatus.NOT_ENROLLED;
        }
        return enrollments[0].status;
      })
    );
  }

  signUp(eventId: number): Observable<Enrollment> {
    const postBody = {
      eventId,
    };
    return this.http.post(`${this.enrollmentBaseUrl}`, postBody).pipe(map((response) => response as Enrollment));
  }
}
