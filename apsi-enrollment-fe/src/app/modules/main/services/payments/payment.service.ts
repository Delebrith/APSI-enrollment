import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Page, PageRequest } from 'src/app/core/model/pagination.model'
import { Payment } from 'src/app/core/model/payment.model'


@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private paymentsUrls = `${environment.apiBaseUrl}/payment`;

  constructor(private http: HttpClient) {}

  getPaymentPage({pageNumber, pageSize}: PageRequest): Observable<Page<Payment>> {
    const params = {
      page: null,
      size: null,
    }
    if (pageNumber != null) {
      params.page = pageNumber;
    } else {
      delete params.page;
    }
    if (pageSize != null) {
      params.size = pageSize;
    } else {
      delete params.size;
    }

    return this.http
      .get<any>(this.paymentsUrls, {params})
      .pipe(
        map(({ content, number: retrievedPageNumber, totalPages, size, totalElements }) => {
          return {
            items: content,
            pageNumber: retrievedPageNumber,
            pageSize: size,
            totalPages,
            totalElements,
          } as Page<Payment>;
        })
      );
  }
}
