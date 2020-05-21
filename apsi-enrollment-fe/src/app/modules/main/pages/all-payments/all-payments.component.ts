import { Component, OnInit, ViewChild } from '@angular/core';
import { Payment } from 'src/app/core/model/payment.model'
import { PaymentService } from 'src/app/modules/main/services/payments/payment.service'
import { ClrDatagridStateInterface } from '@clr/angular';
import { EventDetailComponent } from '../event-detail/event-detail.component';

@Component({
  selector: 'app-all-payments',
  templateUrl: './all-payments.component.html',
  styleUrls: ['./all-payments.component.scss']
})
export class AllPaymentsComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  payments: Payment[];
  totalPayments: number;
  loading = true;

  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {}

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    this.paymentService
      .getPaymentPage({ pageNumber: state.page.current - 1, pageSize: state.page.size })
      .subscribe((page) => {
        this.payments = page.items;
        this.totalPayments = page.totalElements;
        this.loading = false;
      });
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }

}
