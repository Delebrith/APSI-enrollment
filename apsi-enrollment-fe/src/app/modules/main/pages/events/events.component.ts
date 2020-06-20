import {Component, OnInit, ViewChild} from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { BasicEvent } from 'src/app/core/model/event.model';
import { EventService } from '../../services/event/event.service';
import {EventDetailComponent} from '../event-detail/event-detail.component';
import { Observable } from 'rxjs';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';


export abstract class EventsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  events: BasicEvent[];
  totalEvents: number;
  loading = true;

  constructor(protected eventService: EventService) {}

  ngOnInit(): void {}

  abstract getEvents(request: PageRequest): Observable<Page<BasicEvent>>;

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    let searchString: string = null;

    if (state.filters) {
      searchString = state.filters.reduce((prev, next) => `${prev}${next.property}=${next.value},`, '');
      searchString = searchString.substring(0, searchString.length - 1);
    }

    var request = { pageNumber: state.page.current - 1, pageSize: state.page.size, searchQuery: searchString };
    var events = this.getEvents(request);

    events.subscribe((page) => {
        this.events = page.items;
        this.totalEvents = page.totalElements;
        this.loading = false;
      });
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }
}