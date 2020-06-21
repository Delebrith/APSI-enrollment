import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { filter, switchMap, takeUntil, tap } from 'rxjs/operators';
import { APIError } from 'src/app/core/model/api-error.model';
import { environment } from 'src/environments/environment';

@Component({
  templateUrl: './check-attendance.component.html',
  styleUrls: ['./check-attendance.component.css'],
})
export class CheckAttendanceComponent implements OnInit, OnDestroy {
  private subscriptions$: Subject<void>;
  loadingScanner: boolean;
  meetingId: number;
  attendanceId: number;
  code: string;

  scanSuccess: boolean;
  scanError: string;

  constructor(private route: ActivatedRoute, private http: HttpClient) {
    this.subscriptions$ = new Subject<void>();

    this.route.queryParams
      .pipe(
        takeUntil(this.subscriptions$),
        filter((params) => params.code != null),
        tap(() => (this.loadingScanner = true)),
        tap(({ code }) => {
          this.code = code;
          const splitURL = (code as string).split('/');
          if (splitURL.length > 2) {
            this.attendanceId = Number.parseInt(splitURL[1], 10);
          }
        }),
        switchMap(({ code }) => {
          const fullConfirmationURL = environment.apiBaseUrl + '/' + code;
          return this.http.post(fullConfirmationURL, {});
        })
      )
      .subscribe(
        () => {
          this.scanSuccess = true;
          this.loadingScanner = false;
        },
        (error: APIError) => {
          this.scanSuccess = false;
          this.scanError = error.message;
          this.loadingScanner = false;
        }
      );

    this.route.params.pipe(takeUntil(this.subscriptions$)).subscribe((params) => (this.meetingId = params.meetingId));

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
