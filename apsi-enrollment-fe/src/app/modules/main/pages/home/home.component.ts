import {Component, OnInit, ViewChild} from '@angular/core';
import { Observable, of } from 'rxjs';
import { BasicEvent } from 'src/app/core/model/event.model';
import {EventDetailComponent} from '../event-detail/event-detail.component';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  constructor() {}

  ngOnInit(): void {}
}
