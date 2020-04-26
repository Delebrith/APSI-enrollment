import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, of } from 'rxjs';
import { concatMap, expand, map, take, toArray } from 'rxjs/operators';
import { Page } from 'src/app/core/model/pagination.model';
import { Place } from 'src/app/core/model/place.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PlaceService {
  private baseUrl = `${environment.apiBaseUrl}/places`;

  constructor(private http: HttpClient) {}

  getAllPlaces(): Observable<Place[]> {
    const pageSize = 100;

    const params = {
      size: pageSize.toString(),
    };

    return this.getPage(params).pipe(
      expand((page: Page<Place>) => {
        if (page.pageNumber === page.totalPages - 1) {
          return EMPTY;
        } else {
          return this.getPage({ ...params, page: (page.pageNumber + 1).toString() });
        }
      }),
      concatMap((page: Page<Place>) => (page ? page.items : [])),
      toArray(),
      take(1)
    );
  }

  getAvailablePlaces(from: Date, to: Date): Observable<Place[]> {
    const pageSize = 100;

    const params = {
      size: pageSize.toString(),
      searchQuery: `availableBetween=${from.toISOString()};${to.toISOString()}`,
    };

    return this.getPage(params).pipe(
      expand((page: Page<Place>) => {
        if (page.pageNumber === page.totalPages - 1) {
          return EMPTY;
        } else {
          return this.getPage({ ...params, page: (page.pageNumber + 1).toString() });
        }
      }),
      concatMap((page: Page<Place>) => (page ? page.items : [])),
      toArray(),
      take(1)
    );
  }

  private getPage(params: { [key: string]: string }): Observable<Page<Place>> {
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
          } as Page<Place>;
        })
      );
  }
}
