import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-event',
  templateUrl: './new-event.component.html',
  styleUrls: ['./new-event.component.scss'],
})
export class NewEventComponent implements OnInit {
  eventForm: FormGroup;

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

  ngOnInit(): void {}

  submit() {}
}
