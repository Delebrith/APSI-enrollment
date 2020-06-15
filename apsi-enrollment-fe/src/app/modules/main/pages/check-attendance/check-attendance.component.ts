import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  templateUrl: './check-attendance.component.html',
  styleUrls: ['./check-attendance.component.css'],
})
export class CheckAttendanceComponent implements OnInit, OnDestroy {
  private subscriptions$: Subject<void>;
  code: string;
  loadingScanner: boolean;

  constructor(private route: ActivatedRoute) {
    this.subscriptions$ = new Subject<void>();

    this.route.queryParams.pipe(takeUntil(this.subscriptions$)).subscribe((params) => {
      if (params.code != null) {
        this.code = params.code;
      }
    });
    this.loadingScanner = false;
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.subscriptions$.next();
    this.subscriptions$.complete();
  }

  onScanCode() {
    this.loadingScanner = true;
    const origin = location.href.split('?')[0];
    const redirectURI = encodeURI(`${origin}?code={CODE}`);
    const uri = `zxing://scan/?ret=${redirectURI}&SCAN_FORMATS=QR_CODE`;
    console.log(uri);
    window.location.href = uri;
  }

  get isAndroid() {
    return navigator.userAgent.toLowerCase().indexOf('android') !== -1;
  }
}
