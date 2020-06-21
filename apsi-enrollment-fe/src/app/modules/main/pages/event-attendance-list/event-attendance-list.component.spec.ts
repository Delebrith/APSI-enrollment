import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventAttendanceListComponent } from './event-attendance-list.component';

describe('EventAttendanceListComponent', () => {
  let component: EventAttendanceListComponent;
  let fixture: ComponentFixture<EventAttendanceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventAttendanceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventAttendanceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
