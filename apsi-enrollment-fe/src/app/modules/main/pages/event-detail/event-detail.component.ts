import { Component, OnInit } from '@angular/core';
import { switchMap } from 'rxjs/operators';
import { EnrollmentStatus } from 'src/app/core/model/enrollment.model';
import { Event } from 'src/app/core/model/event.model';
import { Payment } from 'src/app/core/model/payment.model';
import { AlertService } from '../../services/alert/alert.service';
import { EventService } from '../../services/event/event.service';
import { PaymentService } from '../../services/payment/payment.service';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css'],
})
export class EventDetailComponent implements OnInit {
  show = false;
  event: Event;
  currency: string;

  enrollmentStatusEnum = EnrollmentStatus;
  enrollmentStatus: EnrollmentStatus;

  constructor(
    private eventService: EventService,
    private paymentService: PaymentService,
    private alertService: AlertService
  ) {
    this.paymentService.getCurrency().subscribe(
      (currency) => (this.currency = currency),
      (error) => this.alertService.showError(error)
    );
  }

  ngOnInit() {}

  open(eventId: number) {
    this.show = true;

    this.eventService
      .getEventById(eventId)
      .pipe()
      .subscribe(
        (event) => (this.event = event),
        (error) => this.alertService.showError(error)
      );

    this.eventService.getEnrollmentStatus(eventId).subscribe(
      (enrollmentStatus) => (this.enrollmentStatus = enrollmentStatus),
      (error) => this.alertService.showError(error)
    );
  }

  onRegister() {
    this.eventService
      .signUp(this.event.id)
      .pipe(switchMap((enrollment) => this.paymentService.create(enrollment)))
      .subscribe(
        (payment) => this.redirectPayment(payment),
        (error) => this.alertService.showError(error)
      );
  }

  onPayNow() {
    this.eventService
      .getEnrollment(this.event.id)
      .pipe(switchMap((enrollment) => this.paymentService.create(enrollment)))
      .subscribe(
        (payment) => this.redirectPayment(payment),
        (error) => this.alertService.showError(error)
      );
  }

  redirectPayment(payment: Payment) {
    window.location.href = payment.redirectUrl;
  }
}
