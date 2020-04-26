import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable } from 'rxjs';
import { concatMap, expand, map, take, toArray } from 'rxjs/operators';
import { Page } from 'src/app/core/model/pagination.model';
import { User } from 'src/app/core/model/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = `${environment.apiBaseUrl}/user`;

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const pageSize = 100;

    const params = {
      size: pageSize.toString(),
    };

    return this.getPage(params).pipe(
      expand((page: Page<User>) => {
        if (page.pageNumber === page.totalPages - 1) {
          return EMPTY;
        } else {
          return this.getPage({ ...params, page: (page.pageNumber + 1).toString() });
        }
      }),
      concatMap((page: Page<User>) => (page ? page.items : [])),
      toArray(),
      take(1)
    );
  }

  getAvailableUsers(from: Date, to: Date): Observable<User[]> {
    const pageSize = 100;

    const params = {
      size: pageSize.toString(),
      searchQuery: `availableBetween:${from.toISOString()};${to.toISOString()}`,
    };

    return this.getPage(params).pipe(
      expand((page: Page<User>) => {
        if (page.pageNumber === page.totalPages - 1) {
          return EMPTY;
        } else {
          return this.getPage({ ...params, page: (page.pageNumber + 1).toString() });
        }
      }),
      concatMap((page: Page<User>) => (page ? page.items : [])),
      toArray(),
      take(1)
    );
  }

  private getPage(params: { [key: string]: string }): Observable<Page<User>> {
    return this.http
      .get<any>(this.baseUrl, { params })
      .pipe(
        map(({ content, number: retrievedPageNumber, totalPages, size, totalElements }) => {
          return {
            items: content,
            pageNumber: retrievedPageNumber,
            pageSize: size,
            totalPages,
            totalElements,
          } as Page<User>;
        })
      );
  }
}
