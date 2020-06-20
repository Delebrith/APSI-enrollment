import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { takeUntil } from 'rxjs/operators';
import { Alert, AlertService } from '../../services/alert/alert.service';

@Component({
  selector: 'app-app-level-alert',
  templateUrl: './app-level-alert.component.html',
  styleUrls: ['./app-level-alert.component.css'],
})
export class AppLevelAlertComponent implements OnInit, OnDestroy {
  private subscriptions$: Subject<void>;
  alerts: Alert[];

  constructor(private alert: AlertService) {
    this.alerts = [];
    this.subscriptions$ = new Subject<void>();
    this.alert.alert$.pipe(takeUntil(this.subscriptions$)).subscribe((x) => {
      console.log(x);
      this.alerts.push(x);
    });
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.subscriptions$.next();
    this.subscriptions$.complete();
  }

  onClose(idx: number) {
    this.alerts.splice(idx, 1);
  }

  cssForAlert(alert: Alert) {
    switch (alert.type) {
      case 'error':
        return 'alert-danger';
      case 'warning':
        return 'alert-warning';
      case 'info':
        return 'alert-info';
    }
  }
}
