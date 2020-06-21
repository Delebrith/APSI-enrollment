import { ApplicationRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import * as jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import * as opns from 'src/app/shared/OpenSans-normal.js';
import { EventService } from '../../services/event/event.service';


@Component({
  selector: 'enrollments-pdf',
  templateUrl: './enrollments-pdf.component.html',
  styleUrls: ['./enrollments-pdf.component.scss']
})
export class EnrollmentsPdfComponent implements OnInit {

  events?: BasicEvent[] = null;
  readonly fileName: string = 'my-enrollments';

  constructor(private eventService: EventService, private appRef: ApplicationRef) { }

  ngOnInit() {}

  onDownloadPDF() {
    this.prepareData().pipe(
      tap(console.log),
      tap((events) => this.events = events),
      tap(() => this.appRef.tick()),
    ).subscribe(() => this.preparePDF());
  }

  private prepareData(): Observable<BasicEvent[]> {
    return (this.events === null) ? this.getEvents().pipe(map((page) => page.items)) : of(this.events);
  }

  private preparePDF() {
      const doc = new jsPDF('landscape');
      opns;
      autoTable(doc, {
        html: '#pdfData',
        styles: {
          font: 'OpenSans',
          fontStyle: 'normal',
        }
      });
      doc.save(`${this.fileName}.pdf`);
  }

  private getEvents(): Observable<Page<BasicEvent>> {
    return this.eventService.getMyEnrolledEventsPage({
      pageNumber: 0,
      pageSize: 10000,
    });
  }

}
