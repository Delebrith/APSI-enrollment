import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Attendance } from 'src/app/core/model/attendance.model';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AttendanceService {
  private attendanceBaseUrl = `${environment.apiBaseUrl}/attendance`;

  constructor(private http: HttpClient) {}

  getMyAttendancePage({ pageNumber, pageSize }: PageRequest): Observable<Page<Attendance>> {
    const params = {
      page: null,
      size: null,
    };
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
      .get<any>(`${this.attendanceBaseUrl}/my`, { params })
      .pipe(
        map(({ content, number: retrievedPageNumber, totalPages, size, totalElements }) => {
          return {
            items: content,
            pageNumber: retrievedPageNumber,
            pageSize: size,
            totalPages,
            totalElements,
          } as Page<Attendance>;
        })
      );
  }

  getQRCode(attendanceId: number) {
    return this.http
      .get(`${this.attendanceBaseUrl}/${attendanceId}/qr-code`, { responseType: 'arraybuffer' })
      .pipe(map((bytes) => this.imageEncode(bytes)));
  }

  getAttendanceListForEvent(eventId: number): Observable<{ number: Attendance[] }> {
    return this.http
      .get<any>(`${this.attendanceBaseUrl}/event/${eventId}`)
      .pipe(map(({ attendanceList }) => attendanceList as { number: Attendance[] }));
  }

  getAttendanceListForMeeting(meetingId: number): Observable<Attendance[]> {
    return this.http
      .get<any>(`${this.attendanceBaseUrl}/meeting/${meetingId}`)
      .pipe(map(({ attendanceList }) => attendanceList as Attendance[]));
  }

  private imageEncode(arrayBuffer) {
    const u8 = new Uint8Array(arrayBuffer);
    const b64encoded = btoa([].reduce.call(new Uint8Array(arrayBuffer), (p, c) => p + String.fromCharCode(c), ''));
    const mimetype = 'image/png';
    return 'data:' + mimetype + ';base64,' + b64encoded;
  }
}
