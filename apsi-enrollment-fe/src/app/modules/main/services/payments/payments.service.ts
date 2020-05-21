import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Page, PageRequest } from 'src/app/core/model/pagination.model'
import { Payment } from 'src/app/core/model/payment.model'

@Injectable({
    providedIn: 'root',
})
export class PaymentService {
    // XXX: Change it when the payments endpoint is done
    private baseUrls = `${environment.apiBaseUrl}/payments`;

    constructor(private http: HttpClient) {}

    getPaymentPage({pageNumber, pageSize}: PageRequest): Observable<Page<Payment>> {
        // Linter marks `of` as deprecated however it shoudn't be.
        // It is a known bug https://github.com/ReactiveX/rxjs/issues/4723.
        // tslint:disable-next-line:deprecation
        return of(this.generateMockPaymentsPage(pageNumber, pageSize));
    }

    private mockedRecordsCount: number = 100

    private generateMockPaymentsPage(pageNumber: number, pageSize: number): Page<Payment> {
        pageNumber -= 1; // get it to 0
        let elementsToGenerate = pageSize;
        if((pageSize * (pageNumber+1)) > this.mockedRecordsCount) {
            elementsToGenerate = this.mockedRecordsCount % pageSize;
        }
        let records = Array.from(
            take(elementsToGenerate, this.generateMockPayments()));
        let page: Page<Payment> = {
            totalElements: this.mockedRecordsCount,
            totalPages: Math.floor((this.mockedRecordsCount / pageSize)) + 1,
            pageNumber: pageNumber + 1,
            pageSize: pageSize,
            items: records,
        };
        return page;
    }

    private *generateMockPayments(): IterableIterator<Payment> {
        while(true) {
            let amount = Math.floor(Math.random()*1000);
            let paid = Math.random() > 0.5;
            yield { amount, paid };
        }
    }
}

function* take<T>(n: number, it: Iterable<T>): IterableIterator<T> {
    for(let val of it) {
        if(n > 0) {
            n--;
            yield val;   
        } else {
            break;
        }
    }
}