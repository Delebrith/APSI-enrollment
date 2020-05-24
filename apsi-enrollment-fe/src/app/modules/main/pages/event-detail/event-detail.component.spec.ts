import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';
import { EventService } from '../../services/event/event.service';
import { PaymentService } from '../../services/payment/payment.service';
import { EventDetailComponent } from './event-detail.component';

const eventService = jasmine.createSpyObj('EventService', ['getEventsPage']);
const paymentService = jasmine.createSpyObj('EventService', { getCurrency: of(null), create: of(null) });

describe('EventDetailComponent', () => {
  let component: EventDetailComponent;
  let fixture: ComponentFixture<EventDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      declarations: [EventDetailComponent],
      providers: [
        { provide: EventService, useValue: eventService },
        { provide: PaymentService, useValue: paymentService },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
