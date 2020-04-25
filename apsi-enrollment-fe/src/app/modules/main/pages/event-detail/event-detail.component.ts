import { Component, OnInit } from '@angular/core';
import {Observable, of} from 'rxjs';
import {BasicEvent} from 'src/app/core/model/event.model';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent implements OnInit {
  show = false;
  eventId: number;
  event$: Observable<BasicEvent>;

  open(eventId: number) {
    this.show = true;
    this.eventId = eventId;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
