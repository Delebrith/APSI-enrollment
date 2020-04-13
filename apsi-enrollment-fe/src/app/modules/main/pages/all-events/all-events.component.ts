import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';

@Component({
  templateUrl: './all-events.component.html',
  styleUrls: ['./all-events.component.css'],
})
export class AllEventsComponent implements OnInit {
  currentEventsPage$: Observable<BasicEvent[]>;

  constructor() {
    this.currentEventsPage$ = of([{ name: 'test' }]);
  }

  ngOnInit(): void {}
}
