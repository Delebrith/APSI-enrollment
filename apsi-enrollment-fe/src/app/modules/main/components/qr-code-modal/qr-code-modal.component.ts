import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../services/attendance/attendance.service';

@Component({
  selector: 'app-qr-code-modal',
  templateUrl: './qr-code-modal.component.html',
  styleUrls: ['./qr-code-modal.component.css'],
})
export class QrCodeModalComponent implements OnInit {
  show: boolean;
  imageSource: string;
  error: boolean;
  constructor(private attendanceService: AttendanceService) {
    this.error = false;
    this.show = false;
    this.imageSource = null;
  }

  ngOnInit() {}

  open(attendanceId: number) {
    this.show = true;
    this.error = false;
    this.imageSource = null;
    this.attendanceService.getQRCode(attendanceId).subscribe(
      (base64URI) => (this.imageSource = base64URI),
      (error) => {
        this.imageSource = 'none';
        this.error = true;
      }
    );
  }

  onClose() {
    this.show = false;
  }
}
