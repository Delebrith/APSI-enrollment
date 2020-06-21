import { Component, OnInit } from '@angular/core';
import { filter, switchMap, tap } from 'rxjs/operators';
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

  isLoading: boolean;

  constructor(
    private eventService: EventService,
    private paymentService: PaymentService,
    private alertService: AlertService
  ) {
    this.paymentService.getCurrency().subscribe(
      (currency) => (this.currency = currency),
      (error) => this.alertService.showError(error)
    );

    this.isLoading = false;
  }

  ngOnInit() {}

  open(eventId: number) {
    this.show = true;
    this.isLoading = false;

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
    this.isLoading = true;
    this.eventService
      .signUp(this.event.id)
      .pipe(
        tap((enrollment) => {
          this.enrollmentStatus = enrollment.status;
          if (enrollment.event.cost === 0) {
            this.isLoading = false;
          }
        }),
        filter((enrollment) => enrollment.event.cost !== 0),
        switchMap((enrollment) => this.paymentService.create(enrollment))
      )
      .subscribe(
        (payment) => this.redirectPayment(payment),
        (error) => {
          this.alertService.showError(error);
          this.isLoading = false;
        }
      );
  }

  onPayNow() {
    this.isLoading = true;
    this.eventService
      .getEnrollment(this.event.id)
      .pipe(switchMap((enrollment) => this.paymentService.create(enrollment)))
      .subscribe(
        (payment) => this.redirectPayment(payment),
        (error) => {
          this.alertService.showError(error);
          this.isLoading = false;
        }
      );
  }

  redirectPayment(payment: Payment) {
    window.location.href = payment.redirectUrl;
  }
}
