import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-new-event',
  templateUrl: './new-event.component.html',
  styleUrls: ['./new-event.component.css']
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

  get meetings() {
    return this.eventForm.get('meetings') as FormArray;
  }

  addMeeting() {
    this.meetings.push(
      this.fb.group({
        description: [null],
        startDate: [null, [Validators.required]],
        startTime: [null, [Validators.required]],
        endDate: [null, [Validators.required]],
        endTime: [null, [Validators.required]],
        speakers: this.fb.array([]),
        place: [null, [Validators.required]],
      })
    );
  }

  deleteMeeting(index: number) {
    this.meetings.removeAt(index);
  }

  speakers(index: number) {
    return this.meetings.at(index).get('speakers') as FormArray;
  }

  addSpeaker(index: number) {
    this.speakers(index).push(
      this.fb.group({
        speaker: ['', [Validators.required]]
      }));
  }

  ngOnInit(): void {
  }

  submit() {}
}
