import { ApplicationRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import * as jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BasicEvent } from 'src/app/core/model/event.model';
import { Page, PageRequest } from 'src/app/core/model/pagination.model';
import roboto from 'src/app/shared/Roboto-Light-normal.js';
import { EventService } from '../../services/event/event.service';


@Component({
  selector: 'enrollments-pdf',
  templateUrl: './enrollments-pdf.component.html',
  styleUrls: ['./enrollments-pdf.component.scss']
})
export class EnrollmentsPdfComponent implements OnInit {

  // @ViewChild('htmlData') htmlData: ElementRef;

  events?: BasicEvent[] = null;
  readonly fileName: string = 'my-enrollments';

  constructor(private eventService: EventService, private appRef: ApplicationRef) { }

  ngOnInit() {}

  onDownloadPDF() {
    const data = this.prepareData();
    data.pipe(
      tap(console.log),
      tap((events) => this.events = events),
      tap(() => this.appRef.tick()),
    ).subscribe(() => this.preparePDF());
  }

  private prepareData(): Observable<BasicEvent[]> {
    const events = (this.events === null) ? this.getEvents().pipe(map((page) => page.items)) : of(this.events);
    return events;
  }

  private preparePDF() {
      // const data = this.htmlData.nativeElement;
      const doc = new jsPDF('landscape');
      doc.addFileToVFS('Roboto-Light-normal.ttf', roboto());
      doc.addFont('Roboto-Light-normal.ttf', 'Roboto-Light', 'normal');
      doc.setFont('Roboto-Light', 'normal');
      autoTable(doc, { html: '#pdfData' });

      // const handleElement = {
      //   '#editor': () => {
      //     return true;
      //   }
      // };
      // doc.fromHTML(data.innerHTML, 10, 10, {
      //   width: 300,
      //   autoSize: true,
      //   elementHandlers: handleElement,
      //   table_font_size: 11,
      // });
      // doc.setFontSize(11);

      doc.save(`${this.fileName}.pdf`);
      // set button as done
  }

  private getEvents(): Observable<Page<BasicEvent>> {
    return this.eventService.getMyEnrolledEventsPage({
      pageNumber: 0,
      pageSize: 10000,
    });
  }

}
