import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CoreModule } from 'src/app/core/core.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MainComponent } from './main.component';
import { MainRoutingModule } from './main.routing';
import { AllEventsComponent } from './pages/all-events/all-events.component';
import { HomeComponent } from './pages/home/home.component';

@NgModule({
  declarations: [HomeComponent, MainComponent, AllEventsComponent],
  imports: [CommonModule, MainRoutingModule, CoreModule, SharedModule],
})
export class MainModule {}
