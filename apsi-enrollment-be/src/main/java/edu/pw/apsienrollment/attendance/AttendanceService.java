package edu.pw.apsienrollment.attendance;

import edu.pw.apsienrollment.event.db.Event;
import org.springframework.data.domain.Page;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    Attendance putIntoAttendanceList(Meeting meeting, User user);

    byte[] getQrCode(Long attendanceId);

    void markAsPresent(Long id, String token, Long meetingId);

    Page<Attendance> getMeetingsOfAuthorizedUser(Integer page, Integer pageSize);

    Map<Long, List<Attendance>> getAttendanceList(Event event);
}
