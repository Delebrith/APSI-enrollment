import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { APIError, APIErrorMessageType } from '../model/api-error.model';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor() {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('interceptor url', req.url);
    if (req.url.startsWith('assets') || req.url.startsWith(`${environment.authBaseUrl}`)) {
      return next.handle(req).pipe(
        catchError((err) => {
          if (err instanceof HttpErrorResponse) {
            const body = err.error;
            const { status, message, messageKey, messageParams } = body;
            return throwError({
              status,
              message,
              messageKey,
              messageParams,
            } as APIError);
          }
          return throwError({
            message: 'Unknown error (client side)',
            messageKey: APIErrorMessageType.UNKNOWN,
            messageParams: {},
            status: 0,
          } as APIError);
        })
      );
    }
    return next.handle(req);
  }
}
