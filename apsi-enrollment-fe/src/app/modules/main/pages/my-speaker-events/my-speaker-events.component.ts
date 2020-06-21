import { Component, OnInit, ViewChild } from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { Observable } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import { EventService } from '../../services/event/event.service';
import { EventDetailComponent } from '../event-detail/event-detail.component';

@Component({
  selector: 'my-speaker-events',
  templateUrl: './my-speaker-events.component.html',
  styleUrls: ['./my-speaker-events.component.scss'],
})
export class MySpeakerEventsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  events: BasicEvent[];
  totalEvents: number;
  loading = true;

  constructor(private eventService: EventService) {}

  getEvents(request: PageRequest): Observable<Page<BasicEvent>> {
    return this.eventService.getMyEventsBySpeakerPage(request)
  }
  ngOnInit(): void {}
  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    let searchString: string = null;

    if (state.filters) {
      searchString = state.filters.reduce(
        (prev, next) => `${prev}${next.property}=${next.value},`, '');
      searchString = searchString.substring(0, searchString.length - 1);
    }

    const request = {
      pageNumber: state.page.current - 1,
      pageSize: state.page.size,
      searchQuery: searchString,
    };
    this.getEvents(request).subscribe((page) => {
        this.events = page.items;
        this.totalEvents = page.totalElements;
        this.loading = false;
      });
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }
}
