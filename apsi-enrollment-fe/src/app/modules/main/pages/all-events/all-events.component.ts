import { Component, OnInit, ViewChild } from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { Observable } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import { AlertService } from '../../services/alert/alert.service';
import { EventService } from '../../services/event/event.service';
import { EventDetailComponent } from '../event-detail/event-detail.component';

@Component({
  templateUrl: './all-events.component.html',
  styleUrls: ['./all-events.component.scss'],
})
export class AllEventsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  events: BasicEvent[];
  totalEvents: number;
  loading = true;

  constructor(private eventService: EventService, private alertService: AlertService) {}

  getEvents(request: PageRequest): Observable<Page<BasicEvent>> {
    return this.eventService.getEventsPage(request);
  }

  ngOnInit(): void {}

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    let searchString: string = null;

    if (state.filters) {
      searchString = state.filters.reduce((prev, next) => `${prev}${next.property}=${next.value},`, '');
      searchString = searchString.substring(0, searchString.length - 1);
    }

    const request = {
      pageNumber: state.page.current - 1,
      pageSize: state.page.size,
      searchQuery: searchString,
    };
    this.getEvents(request).subscribe(
      (page) => {
        this.events = page.items;
        this.totalEvents = page.totalElements;
        this.loading = false;
      },
      (error) => {
        this.alertService.showError(error);
        this.events = [];
        this.totalEvents = 0;
        this.loading = false;
      }
    );
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }
}
