import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';
import { APIError } from 'src/app/core/model/api-error.model';

export interface Alert {
  type: 'error' | 'warning' | 'info';
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private alertSubject$: Subject<Alert>;
  alert$: Observable<Alert>;

  constructor(translate: TranslateService) {
    this.alertSubject$ = new Subject<Alert>();
    this.alert$ = this.alertSubject$.asObservable();
  }

  showAlert(alert: Alert) {
    this.alertSubject$.next(alert);
  }

  showError(error: APIError) {
    this.alertSubject$.next({
      type: 'error',
      message: error.message,
    });
  }
}
