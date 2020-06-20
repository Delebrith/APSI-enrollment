import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import { Observable } from 'rxjs';
import { Attendance } from 'src/app/core/model/attendance.model';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
  })
export class AttendanceService {
  private attendanceBaseUrl = `${environment.apiBaseUrl}/attendance`;
  
  constructor(private http: HttpClient) {}

  getMyAttendancePage({
    pageNumber,
    pageSize,
  }: PageRequest): Observable<Page<Attendance>> {
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

}