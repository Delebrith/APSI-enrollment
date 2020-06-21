import { ApplicationRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import * as jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Attendance } from 'src/app/core/model/attendance.model';
import { Page } from 'src/app/core/model/pagination.model';
import * as opns from 'src/app/shared/OpenSans-normal.js';
import { AttendanceService } from '../../services/attendance/attendance.service';

@Component({
  selector: 'app-attendance-pdf',
  templateUrl: './attendance-pdf.component.html',
  styleUrls: ['./attendance-pdf.component.scss'],
})
export class AttendancePdfComponent implements OnInit {
  attendance?: Attendance[] = null;
  readonly fileName: string = 'my-attendance';
  constructor(private attendanceService: AttendanceService, private appRef: ApplicationRef) {}

  ngOnInit() {}

  onDownloadPDF() {
    this.prepareData()
      .pipe(
        tap(console.log),
        tap((attendance) => (this.attendance = attendance)),
        tap(() => this.appRef.tick())
      )
      .subscribe(() => this.preparePDF());
  }

  private prepareData(): Observable<Attendance[]> {
    return this.attendance === null ? this.getAttendance().pipe(map((page) => page.items)) : of(this.attendance);
  }

  private preparePDF() {
    const doc = new jsPDF('landscape');
    // tslint:disable-next-line: no-unused-expression
    opns;
    autoTable(doc, {
      html: '#pdfData',
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
