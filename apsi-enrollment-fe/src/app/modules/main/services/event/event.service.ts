import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BasicEvent, Event, Meeting } from 'src/app/core/model/event.model';
import { Page } from 'src/app/core/model/pagination.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private baseUrl = `${environment.apiBaseUrl}/events`;

  constructor(private http: HttpClient) {}

  getEventsPage(pageNumber?: number): Observable<Page<BasicEvent>> {
    return this.http.get<any>(this.baseUrl).pipe(
      map(({ content, number: retrievedPageNumber, totalPages, size }) => {
        return {
          items: content,
          pageNumber: retrievedPageNumber,
          pageSize: size,
          totalPages,
        } as Page<BasicEvent>;
      })
    );
  }

  getEventByID(eventID: number): Observable<Event> {
    return this.http.get(`${this.baseUrl}/${eventID}`).pipe(
      map((response) => {
        return null;
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

    return this.http.post(`${this.baseUrl}`, event).pipe(map((response) => response as Event));
  }
}
