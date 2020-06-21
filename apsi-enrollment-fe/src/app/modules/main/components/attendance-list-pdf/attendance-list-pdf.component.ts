import { ApplicationRef, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import * as jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Attendance } from 'src/app/core/model/attendance.model';
import { Page } from 'src/app/core/model/pagination.model';
import * as opns from 'src/app/shared/OpenSans-normal.js';
import { AttendanceService } from '../../services/attendance/attendance.service';

@Component({
  selector: 'app-attendance-list-pdf',
  templateUrl: './attendance-list-pdf.component.html',
  styleUrls: ['./attendance-list-pdf.component.scss'],
})
export class AttendanceListPdfComponent implements OnInit {
  @Input() meetingId: number;
  attendance?: Attendance[] = null;
  pdfDataId: string;
  readonly fileName: string = 'attendance-list';

  constructor(private attendanceService: AttendanceService, private appRef: ApplicationRef) {
    this.pdfDataId = 'pdfData' + Math.random().toString(36).substring(7);
  }

  ngOnInit() {}

  onDownloadPDF() {
    this.prepareData()
      .pipe(
        tap((attendance) => (this.attendance = attendance)),
        tap(() => this.appRef.tick())
      )
      .subscribe(() => this.preparePDF());
  }

  private prepareData(): Observable<Attendance[]> {
    return this.attendance === null
      ? this.attendanceService.getAttendanceListForMeeting(this.meetingId)
      : of(this.attendance);
  }

  private preparePDF() {
    const doc = new jsPDF('landscape');
    // tslint:disable-next-line: no-unused-expression
    opns;
    autoTable(doc, {
      html: `#${this.pdfDataId}`,
      styles: {
        font: 'OpenSans',
        fontStyle: 'normal',
      },
    });
    doc.save(`${this.fileName}.pdf`);
  }

  private getAttendance(): Observable<Page<Attendance>> {
    return this.attendanceService.getMyAttendancePage({
      pageNumber: 0,
      pageSize: 10000,
    });
  }
}
