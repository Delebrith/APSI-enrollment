package edu.pw.apsienrollment.attendance;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;

public interface AttendanceService {
    Attendance putIntoAttendanceList(Meeting meeting, User user);

    byte[] getQrCode(Long attendanceId);

    void markAsPresent(Long id, String token);
}
