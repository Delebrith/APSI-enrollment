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
import { Subject, timer } from 'rxjs';
import { debounce, takeUntil } from 'rxjs/operators';
import { EventType } from '../../../../core/model/event.model';
import { Place } from '../../../../core/model/place.model';
import { User } from '../../../../core/model/user.model';
import { EventService } from '../../services/event/event.service';
import { PlaceService } from '../../services/place/place.service';
import { UserService } from '../../services/user/user.service';

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
  eventTypes = EventType;
  availablePlaces: Place[][] = [];
  availableSpeakers: User[][] = [];

  subscriptions$: Subject<void>;

  constructor(
    private fb: FormBuilder,
    private eventService: EventService,
    private placeService: PlaceService,
    private userService: UserService
  ) {
    this.subscriptions$ = new Subject<void>();

    this.eventForm = fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      eventType: [null, [Validators.required]],
      attendeesLimit: [null, [Validators.required]],
      meetings: this.fb.array([]),
    });

    this.getMeetings()
      .valueChanges.pipe(
        debounce(() => timer(500)),
        takeUntil(this.subscriptions$)
      )
      .subscribe((data) => {
        this.getMeetings().controls.forEach((meeting, index) => {
          let start: Date;
          let end: Date;
          if (meeting.get('startDate').valid && meeting.get('startTime').valid) {
            start = parseDate(meeting.get('startDate').value, meeting.get('startTime').value);
          }
          if (meeting.get('endDate').valid && meeting.get('endTime').valid) {
            end = parseDate(meeting.get('endDate').value, meeting.get('endTime').value);
          }
          if (start && end) {
            this.placeService.getAvailablePlaces(start, end).subscribe((places) => {
              this.availablePlaces[index] = places;
            });
            this.userService.getAvailableUsers(start, end).subscribe((users) => {
              this.availableSpeakers[index] = users;
            });
          } else {
            this.placeService.getAllPlaces().subscribe((places) => {
              this.availablePlaces[index] = places;
            });
            this.userService.getAllUsers().subscribe((users) => {
              this.availableSpeakers[index] = users;
            });
          }
        });
      });
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

  submit() {}
}
