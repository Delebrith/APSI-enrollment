import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BasicEvent, Event, Meeting } from 'src/app/core/model/event.model';
import { Page } from 'src/app/core/model/pagination.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private baseUrl = `${environment.apiBaseUrl}/events`;

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
      .get<any>(this.baseUrl, { params })
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

  getEventByID(eventId: number): Observable<Event> {
    return this.http.get<any>(`${this.baseUrl}/${eventId}`).pipe(
      map(({ name, description, eventType, attendeesLimit, meetings }) => {
        return {
          name,
          description,
          eventType,
          attendeesLimit,
          meetings,
        } as Event;
      })
    );
  }

  createNewEvent(event: Event): Observable<Event> {
    const postBody = {
      ...event,
      meetings: event.meetings.map((meeting: Meeting) => ({
        ...meeting,
        start: meeting.start.toISOString(),
        end: meeting.end.toISOString(),
      })),
    };

    return this.http.post(`${this.baseUrl}`, postBody).pipe(map((response) => response as Event));
  }
}
