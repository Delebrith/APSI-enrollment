import { Component, OnInit } from '@angular/core';
import { Attendance, AttendanceStatus } from '../../../../core/model/attendance.model';
import { Event } from 'src/app/core/model/event.model';
import { ActivatedRoute } from '@angular/router';
import { AttendanceService } from '../../services/attendance/attendance.service';
import {EventService} from '../../services/event/event.service';

@Component({
  selector: 'app-event-attendance-list',
  templateUrl: './event-attendance-list.component.html',
  styleUrls: ['./event-attendance-list.component.css']
})
export class EventAttendanceListComponent implements OnInit {
  show = false;
  event: Event;
  currency: string;
  attendanceList: { number: Attendance[] };
  attendanceStatus = AttendanceStatus;

  constructor(private eventService: EventService, private attendanceService: AttendanceService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const eventId = +this.route.snapshot.paramMap.get('id');
    this.show = true;

    this.eventService
      .getEventById(eventId)
      .pipe()
      .subscribe((event) => {
        this.event = event;
      });
    this.attendanceService.getAttendanceList(eventId).subscribe((attendanceList) => {
      this.attendanceList = attendanceList;
    });
  }

  getAttendanceList(meetingId: number) {
    return this.attendanceList[meetingId] as Attendance[]
  }

}
