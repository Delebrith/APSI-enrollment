import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BasicEvent, Event, EventType } from 'src/app/core/model/event.model';
import { Page } from 'src/app/core/model/pagination.model';
import { EventService } from '../../services/event/event.service';

@Component({
  templateUrl: './all-events.component.html',
  styleUrls: ['./all-events.component.scss'],
})
export class AllEventsComponent implements OnInit {
  currentEventsPage$: Observable<BasicEvent[]>;

  currentPage$: Observable<Page<Event>>;

  constructor(private eventService: EventService) {
    eventService.getEventsPage().subscribe(console.log);

    this.currentEventsPage$ = of([{ name: 'test' } as BasicEvent]);
  }

  ngOnInit(): void {}
}
