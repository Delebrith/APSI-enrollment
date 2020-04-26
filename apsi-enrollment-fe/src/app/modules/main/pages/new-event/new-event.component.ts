import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {EventType} from '../../../../core/model/event.model';

export const timeValidator: ValidatorFn = (formGroup: FormGroup): ValidationErrors | null => {
  const startDate = formGroup.get('startDate').value;
  const startTime = formGroup.get('startTime').value;
  const endDate = formGroup.get('endDate').value;
  const endTime = formGroup.get('endTime').value;
  if (startDate && startTime && endDate && endTime) {
    const start = parseDate(startDate, startTime);
    const end = parseDate(endDate, endTime);
    if (start > end) {
      return {
        time: true
      };
    }
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
        startDate: [null, [Validators.required]],
        startTime: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
        endTime: [null, [Validators.required]],
        speakers: this.fb.array([]),
        place: [null, [Validators.required]],
      },
      {
        validator: timeValidator,
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
