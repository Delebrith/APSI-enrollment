import { Component, OnInit, ViewChild } from '@angular/core';
import { ClrDatagridStateInterface } from '@clr/angular';
import { Attendance } from 'src/app/core/model/attendance.model';
import { AttendanceService } from '../../services/attendance/attendance.service';
import { EventDetailComponent } from '../event-detail/event-detail.component';

@Component({
  templateUrl: './my-attendances.component.html',
  styleUrls: ['./my-attendances.component.scss'],
})
export class MyAttendancesComponent implements OnInit {
  @ViewChild(EventDetailComponent) modal: EventDetailComponent;
  attendances: Attendance[];
  totalAttendances: number;
  loading = true;

  constructor(private attendanceService: AttendanceService) {}
  ngOnInit(): void {}

  onDgRefresh(state: ClrDatagridStateInterface) {
    this.loading = true;
    let searchString: string = null;

    if (state.filters) {
      searchString = state.filters.reduce(
        (prev, next) => `${prev}${next.property}=${next.value},`, '');
      searchString = searchString.substring(0, searchString.length - 1);
    }

    const request = {
      pageNumber: state.page.current - 1,
      pageSize: state.page.size,
      searchQuery: searchString,
    };
    this.attendanceService.getMyAttendancePage(request).subscribe((page) => {
        this.attendances = page.items;
        this.totalAttendances = page.totalElements;
        this.loading = false;
      });
  }

  onShowEventDetails(eventId: number) {
    this.modal.open(eventId);
  }
}
