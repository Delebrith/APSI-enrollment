import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import {CoreModule} from 'src/app/core/core.module';
import {SharedModule} from 'src/app/shared/shared.module';
import {MainComponent} from './main.component';
import {MainRoutingModule} from './main.routing';
import {AllEventsComponent} from './pages/all-events/all-events.component';
import {EventDetailComponent} from './pages/event-detail/event-detail.component';
import {HomeComponent} from './pages/home/home.component';
import {NewEventComponent} from './pages/new-event/new-event.component';
import { AllPaymentsComponent } from './pages/all-payments/all-payments.component';

@NgModule({
  declarations: [HomeComponent, MainComponent, AllEventsComponent, EventDetailComponent, NewEventComponent, AllPaymentsComponent],
  imports: [CommonModule, MainRoutingModule, CoreModule, SharedModule, ReactiveFormsModule],
})
export class MainModule {
}
