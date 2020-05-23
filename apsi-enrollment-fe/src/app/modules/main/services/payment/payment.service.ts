import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Enrollment } from 'src/app/core/model/enrollment.model';
import { Payment } from 'src/app/core/model/payment.model';

@Injectable({
    providedIn: 'root',
  })
export class PaymentService {
    private baseUrl = `${environment.apiBaseUrl}/payment`;

    constructor(private http: HttpClient) {}

    getCurrency(): Observable<String> {
        return this.http
            .get<any>(`${this.baseUrl}/currency`)
            .pipe(map((response) => response.currency as String));
    }

    create(enrollment: Enrollment): Observable<Payment> {
        const postBody = {
            enrollmentId: enrollment.id
        }

        return this.http.post(`${this.baseUrl}`, postBody).pipe(map((response) => response as Payment))
    }
}