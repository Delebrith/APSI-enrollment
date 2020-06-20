import { EventsComponent } from '../events/events.component';
import { EventService } from '../../services/event/event.service';
import { PageRequest, Page } from 'src/app/core/model/pagination.model';
import { Observable } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Component } from '@angular/core';

@Component({
  selector: 'my-organizer-events',
  templateUrl: '../events/events.component.html',
  styleUrls: ['../events/events.component.scss'],
})
export class MyOrganizerEventsComponent extends EventsComponent {
  constructor(eventService: EventService) {super(eventService)}

  getEvents(request: PageRequest): Observable<Page<BasicEvent>> {
    return this.eventService.getMyEventsByOrganizerPage(request)
  }
}