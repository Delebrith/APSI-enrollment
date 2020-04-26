import { Component, OnInit } from '@angular/core';
import { Event} from 'src/app/core/model/event.model';
import {EventService} from '../../services/event/event.service';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent implements OnInit {
  show = false;
  event: Event;

  open(eventId: number) {
    this.show = true;
    this.eventService
      .getEventById(eventId)
      .subscribe(event => {
        this.event = event;
      });
  }

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
  }

}
