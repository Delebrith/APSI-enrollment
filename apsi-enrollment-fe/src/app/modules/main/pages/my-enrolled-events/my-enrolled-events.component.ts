import { EventService } from '../../services/event/event.service';
import { PageRequest, Page } from 'src/app/core/model/pagination.model';
import { Observable } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Component, ViewChild, OnInit } from '@angular/core';
import { EventDetailComponent } from '../event-detail/event-detail.component';
import { ClrDatagridStateInterface } from '@clr/angular';

@Component({
  selector: 'my-enrolled-events',
  templateUrl: './my-enrolled-events.component.html',
  styleUrls: ['./my-enrolled-events.component.scss'],
})
export class MyEnrolledEventsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  events: BasicEvent[];
  totalEvents: number;
  loading = true;

  constructor(private eventService: EventService) {}

  getEvents(request: PageRequest): Observable<Page<BasicEvent>> {
    return this.eventService.getMyEnrolledEventsPage(request)
  }
  
  ngOnInit(): void {}
  
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