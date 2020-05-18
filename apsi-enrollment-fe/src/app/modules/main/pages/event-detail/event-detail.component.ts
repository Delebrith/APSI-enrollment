import { Component, OnInit } from '@angular/core';
import { EnrollmentStatus } from 'src/app/core/model/enrollment.model';
import { Event } from 'src/app/core/model/event.model';
import { EventService } from '../../services/event/event.service';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css'],
})
export class EventDetailComponent implements OnInit {
  show = false;
  event: Event;

  enrollmentStatusEnum = EnrollmentStatus;
  enrollmentStatus: EnrollmentStatus;

  constructor(private eventService: EventService) {}

  ngOnInit() {}

  open(eventId: number) {
    this.show = true;
    this.eventService.getEventById(eventId).subscribe((event) => {
      this.event = event;
    });
    this.eventService.getEnrollmentStatus(eventId).subscribe((enrollmentStatus) => {
      this.enrollmentStatus = enrollmentStatus;
    });
  }

  onRegister() {
    this.eventService.signUp(this.event.id).subscribe(
      (enrollment) => {
        this.enrollmentStatus = enrollment.status;
        // TODO: redirect to payment page
      },
      (error) => {
        console.error(error);
        // TODO: use API error to display error message alert
      }
    );
  }

  onPay() {
    // TODO: redirect to payment page
  }
}
