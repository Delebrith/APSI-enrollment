import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main.component';
import { AllEventsComponent } from './pages/all-events/all-events.component';
import { MyEventsComponent } from './pages/my-events/my-events.component';
import { AllPaymentsComponent } from './pages/all-payments/all-payments.component';
import { HomeComponent } from './pages/home/home.component';
import { NewEventComponent } from './pages/new-event/new-event.component';
import { MyAttendancesComponent } from './pages/my-attendances/my-attendances.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent,
      },
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
        path: 'my-attendances',
        component: MyAttendancesComponent,
      },
      {
        path: '**',
        pathMatch: 'full',
        redirectTo: 'home',
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
