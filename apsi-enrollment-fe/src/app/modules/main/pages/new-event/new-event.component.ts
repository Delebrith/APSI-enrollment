import {Component, OnInit} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {EventType} from '../../../../core/model/event.model';

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
        dateDependence: true
      };
    }
  }
};

export const dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  if (control && control.value && !Date.parse(control.value)) {
    return {dateFormat: true};
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
export class NewEventComponent implements OnInit {
  eventForm: FormGroup;
  eventTypes = EventType;

  constructor(private fb: FormBuilder) {
    this.eventForm = fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      eventType: [null, [Validators.required]],
      attendeesLimit: [null, [Validators.required]],
      meetings: this.fb.array([]),
    });
  }

  getMeetings() {
    return this.eventForm.get('meetings') as FormArray;
  }

  addMeeting() {
    const meetingGroup = this.fb.group({
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
      });
    this.getMeetings().push(meetingGroup);
  }

  deleteMeeting(index: number) {
    this.getMeetings().removeAt(index);
  }

  getSpeakers(index: number) {
    return this.getMeetings().at(index).get('speakers') as FormArray;
  }

  addSpeaker(index: number) {
    this.getSpeakers(index).push(
      this.fb.group({
        speaker: ['', [Validators.required]],
      })
    );
  }

  ngOnInit(): void {
  }

  submit() {
  }
}
