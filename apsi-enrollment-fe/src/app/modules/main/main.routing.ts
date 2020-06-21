import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventGuard } from './guards/event-attendance-list.guard';
import { MainComponent } from './main.component';
import { AllEventsComponent } from './pages/all-events/all-events.component';
import { AllPaymentsComponent } from './pages/all-payments/all-payments.component';
import { EventAttendanceListComponent } from './pages/event-attendance-list/event-attendance-list.component';
import { CheckAttendanceComponent } from './pages/check-attendance/check-attendance.component';
import { MyAttendancesComponent } from './pages/my-attendances/my-attendances.component';
import { MyEventsComponent } from './pages/my-events/my-events.component';
import { NewEventComponent } from './pages/new-event/new-event.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'all-events',
        component: AllEventsComponent,
      },
      {
        path: 'my-events',
        component: MyEventsComponent,
      },
      {
        path: 'new-event',
        component: NewEventComponent,
      },
      {
        path: 'all-payments',
        component: AllPaymentsComponent,
      },
      {
        path: 'check-attendance/:meetingId',
        component: CheckAttendanceComponent,
      },
      {
        path: 'my-attendances',
        component: MyAttendancesComponent,
      },
      {
        path: 'event/:id',
        component: EventAttendanceListComponent,
        canActivate: [EventGuard],
      },
      {
        path: '**',
        pathMatch: 'full',
        redirectTo: 'my-events',
      },
    ],
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
