import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AppLevelAlertComponent } from './components/app-level-alert/app-level-alert.component';
import { AttendancePdfComponent } from './components/attendance-pdf/attendance-pdf.component';
import { EnrollmentsPdfComponent } from './components/enrollments-pdf/enrollments-pdf.component';
import { MainComponent } from './main.component';
import { MainRoutingModule } from './main.routing';
import { AllEventsComponent } from './pages/all-events/all-events.component';
import { AllPaymentsComponent } from './pages/all-payments/all-payments.component';
import { EventDetailComponent } from './pages/event-detail/event-detail.component';
import { HomeComponent } from './pages/home/home.component';
import { MyAttendancesComponent } from './pages/my-attendances/my-attendances.component';
import { MyEnrolledEventsComponent } from './pages/my-enrolled-events/my-enrolled-events.component';
import { MyEventsComponent } from './pages/my-events/my-events.component';
import { MyOrganizerEventsComponent } from './pages/my-organizer-events/my-organizer-events.component';
import { MySpeakerEventsComponent } from './pages/my-speaker-events/my-speaker-events.component';
import { NewEventComponent } from './pages/new-event/new-event.component';

@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    AllEventsComponent,
    MyEventsComponent,
    MySpeakerEventsComponent,
    MyOrganizerEventsComponent,
    AllPaymentsComponent,
    EventDetailComponent,
    NewEventComponent,
    AppLevelAlertComponent,
    MyEnrolledEventsComponent,
    MyAttendancesComponent,
    EnrollmentsPdfComponent,
    AttendancePdfComponent,
  ],
  imports: [CommonModule, MainRoutingModule, CoreModule, SharedModule, ReactiveFormsModule],
})
export class MainModule {}
