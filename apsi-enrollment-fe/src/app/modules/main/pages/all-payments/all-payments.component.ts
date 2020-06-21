import { Component, OnInit, ViewChild } from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { Payment } from 'src/app/core/model/payment.model';
import { PaymentService } from 'src/app/modules/main/services/payments/payment.service';
import { AlertService } from '../../services/alert/alert.service';
import { EventDetailComponent } from '../event-detail/event-detail.component';

@Component({
  selector: 'app-all-payments',
  templateUrl: './all-payments.component.html',
  styleUrls: ['./all-payments.component.scss'],
})
export class AllPaymentsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  payments: Payment[];
  totalPayments: number;
  loading = true;

  constructor(private paymentService: PaymentService, private alertService: AlertService) {}

  ngOnInit(): void {}

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    this.paymentService.getPaymentPage({ pageNumber: state.page.current - 1, pageSize: state.page.size }).subscribe(
      (page) => {
        this.payments = page.items;
        this.totalPayments = page.totalElements;
        this.loading = false;
      },
      (error) => {
        this.alertService.showError(error);
        this.payments = [];
        this.totalPayments = 0;
        this.loading = false;
      }
    );
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }
}
