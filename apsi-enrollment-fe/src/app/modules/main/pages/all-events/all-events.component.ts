import { Component, OnInit } from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { BasicEvent, Event, EventType } from 'src/app/core/model/event.model';
import { Page } from 'src/app/core/model/pagination.model';
import { EventService } from '../../services/event/event.service';

@Component({
  templateUrl: './all-events.component.html',
  styleUrls: ['./all-events.component.scss'],
})
export class AllEventsComponent implements OnInit {
  events: BasicEvent[];
  totalEvents: number;
  loading = true;

  constructor(private eventService: EventService) {}

  ngOnInit(): void {}

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    let searchString: string = null;

    if (state.filters) {
      searchString = state.filters.reduce((prev, next) => `${prev}${next.property}=${next.value},`, '');
      searchString = searchString.substring(0, searchString.length - 1);
    }

    this.eventService
      .getEventsPage({ pageNumber: state.page.current - 1, pageSize: state.page.size, searchQuery: searchString })
      .subscribe((page) => {
        this.events = page.items;
        this.totalEvents = page.totalElements;
        this.loading = false;
      });
  }

  onShowEventDetails(eventId: number) {
    console.error(`not implemented: show event details of event ${eventId}`);
  }
}
