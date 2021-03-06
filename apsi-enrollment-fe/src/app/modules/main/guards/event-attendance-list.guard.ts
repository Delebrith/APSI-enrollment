import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { combineLatest, Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { CurrentUserService } from '../../../core/auth/current-user.service';
import { EventService } from '../services/event/event.service';

@Injectable()
export class EventGuard implements CanActivate {
  constructor(
    private eventService: EventService,
    private currentUserService: CurrentUserService,
    private router: Router
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const event$ = this.eventService.getEventById(next.params.id);
    const currentUser$ = this.currentUserService.currentUser$;
    return combineLatest([event$, currentUser$]).pipe(
      map(([event, currentUser]) => {
        return event.organizer.id === currentUser.id ||
          event.meetings.reduce((prev, next) => ([...prev, ...next.speakers]), []).some(speaker => speaker.id === currentUser.id)
      }),
      take(1),
    );
  }
}
