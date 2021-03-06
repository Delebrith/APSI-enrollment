import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { combineLatest, Subject, timer } from 'rxjs';
import { debounce, filter, map, switchAll, takeUntil } from 'rxjs/operators';
import { APIError, APIErrorMessageType } from 'src/app/core/model/api-error.model';
import { CurrentUserService } from '../../../../core/auth/current-user.service';
import { EventRequest, EventType, MeetingRequest } from '../../../../core/model/event.model';
import { Place } from '../../../../core/model/place.model';
import { User } from '../../../../core/model/user.model';
import { AlertService } from '../../services/alert/alert.service';
import { EventService } from '../../services/event/event.service';
import { PaymentService } from '../../services/payment/payment.service';
import { PlaceService } from '../../services/place/place.service';
import { UserService } from '../../services/user/user.service';

export const noMeetingValidator: ValidatorFn = (formArray: FormArray): ValidationErrors | null => {
  if (formArray && formArray.length === 0) {
    return {
      noMeeting: true,
    };
  }
};

export const dateDependenceValidator: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  const startDate = formGroup.get('startDate').value;
  const startTime = formGroup.get('startTime').value;
  const endDate = formGroup.get('endDate').value;
  const endTime = formGroup.get('endTime').value;
  if (startDate && startTime && endDate && endTime) {
    const start = parseDate(startDate, startTime);
    const end = parseDate(endDate, endTime);
    if (start > end) {
      return {
        dateDependence: true,
      };
    }
  }
};

export const dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  if (control && control.value && !Date.parse(control.value)) {
    return { dateFormat: true };
  }
};

function parseDate(date: string, time: string) {
  if (!date || !time || isNaN(Date.parse(date)) || time.indexOf(':') === -1) {
    return null;
  }
  const d = new Date(date);
  const splitTime = time.split(':');
  d.setHours(Number(splitTime[0]));
  d.setMinutes(Number(splitTime[1]));
  return d;
}

@Component({
  selector: 'app-new-event',
  templateUrl: './new-event.component.html',
  styleUrls: ['./new-event.component.scss'],
})
export class NewEventComponent implements OnInit, OnDestroy {
  eventForm: FormGroup;
  eventTypes: EventType[];
  availablePlaces: Place[][] = [];
  availableSpeakers: User[][] = [];
  createError: APIError | null;
  currency: string;

  subscriptions$: Subject<void>;

  constructor(
    private fb: FormBuilder,
    private currentUserService: CurrentUserService,
    private paymentService: PaymentService,
    private eventService: EventService,
    private placeService: PlaceService,
    private alertService: AlertService,
    private userService: UserService,
    private router: Router
  ) {
    this.subscriptions$ = new Subject<void>();

    this.paymentService.getCurrency().subscribe((currency) => (this.currency = currency));

    this.eventForm = fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      eventType: [null, [Validators.required]],
      attendeesLimit: [null, [Validators.required]],
      meetings: this.fb.array([], [noMeetingValidator]),
      cost: [0, []],
    });

    const allowedToCreate$ = eventService.getAllowedToCreate();
    const currentUser$ = currentUserService.currentUser$;
    combineLatest([allowedToCreate$, currentUser$])
      .pipe(filter(([allowedToCreate, currentUser]) => currentUser != null))
      .subscribe(([allowedToCreate, currentUser]) => {
        this.eventTypes = Object.entries(allowedToCreate)
          .filter(([eventType, roles]) => roles.some((x) => currentUser.userRoles.includes(x)))
          .map(([eventType, roles]) => eventType as EventType);
      });

    this.getMeetings()
      .valueChanges.pipe(
        debounce(() => timer(500)),
        map((data) => {
          return data.map((meeting, idx) => {
            const start = parseDate(meeting.startDate, meeting.startTime);
            const end = parseDate(meeting.endDate, meeting.endTime);
            return { idx, start, end };
          });
        }),
        switchAll(),
        takeUntil(this.subscriptions$)
      )
      .subscribe(({ start, end, idx }) => {
        if (start && end) {
          this.placeService.getAvailablePlaces(start, end).subscribe((places) => {
            this.availablePlaces[idx] = places;
          });
          this.userService.getAvailableUsers(start, end).subscribe((users) => {
            this.availableSpeakers[idx] = users;
          });
        } else {
          this.placeService.getAllPlaces().subscribe((places) => {
            this.availablePlaces[idx] = places;
          });
          this.userService.getAllUsers().subscribe((users) => {
            this.availableSpeakers[idx] = users;
          });
        }
      });
    this.addMeeting();
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.subscriptions$.next();
    this.subscriptions$.complete();
  }

  getMeetings() {
    return this.eventForm.get('meetings') as FormArray;
  }

  addMeeting() {
    const meetingGroup = this.fb.group(
      {
        description: [null],
        startDate: [null, [Validators.required, dateValidator]],
        startTime: [null, [Validators.required]],
        endDate: [null, [Validators.required, dateValidator]],
        endTime: [null, [Validators.required]],
        speakers: this.fb.array([]),
        place: [null, [Validators.required]],
      },
      {
        validator: dateDependenceValidator,
      }
    );
    this.getMeetings().push(meetingGroup);
    this.availablePlaces.push([]);
    this.availableSpeakers.push([]);
  }

  deleteMeeting(index: number) {
    this.getMeetings().removeAt(index);
    this.availablePlaces.splice(index, 1);
    this.availableSpeakers.splice(index, 1);
  }

  getSpeakers(index: number) {
    return this.getMeetings().at(index).get('speakers') as FormArray;
  }

  addSpeaker(index: number) {
    this.getSpeakers(index).push(
      this.fb.group({
        speaker: [null, [Validators.required]],
      })
    );
  }

  submit() {
    const meetingList: MeetingRequest[] = [];
    this.getMeetings().controls.forEach((meeting, index) => {
      const speakerList: number[] = [];
      (meeting.get('speakers') as FormArray).controls.forEach((speaker, index2) => {
        speakerList.push(speaker.get('speaker').value);
      });
      meetingList.push({
        description: meeting.get('description').value,
        start: parseDate(meeting.get('startDate').value, meeting.get('startTime').value),
        end: parseDate(meeting.get('endDate').value, meeting.get('endTime').value),
        placeId: meeting.get('place').value,
        speakerIds: speakerList,
      });
    });
    const newEvent: EventRequest = {
      name: this.eventForm.get('name').value,
      description: this.eventForm.get('description').value,
      eventType: this.eventForm.get('eventType').value,
      attendeesLimit: this.eventForm.get('attendeesLimit').value,
      meetings: meetingList,
      cost: this.eventForm.get('cost').value,
    };
    this.eventService.createNewEvent(newEvent).subscribe(
      () => {
        this.router.navigate(['/main/all-events']);
      },
      (error: APIError) => {
        this.createError = error;
        this.alertService.showError(error);
      }
    );
  }
}
