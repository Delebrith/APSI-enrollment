import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/core/model/payment.model'
import { PaymentService } from 'src/app/modules/main/services/payments/payments.service'
import { ClrDatagridStateInterface } from '@clr/angular';

@Component({
  selector: 'app-all-payments',
  templateUrl: './all-payments.component.html',
  styleUrls: ['./all-payments.component.scss']
})
export class AllPaymentsComponent implements OnInit {

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

}
