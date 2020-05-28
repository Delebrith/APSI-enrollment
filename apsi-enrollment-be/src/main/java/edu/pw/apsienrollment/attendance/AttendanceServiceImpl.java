package edu.pw.apsienrollment.attendance;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.attendance.db.AttendanceRepository;
import edu.pw.apsienrollment.attendance.db.AttendanceStatus;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public Attendance putIntoAttendanceList(Meeting meeting, User user) {
        Attendance attendance = Attendance.builder()
                .meeting(meeting)
                .user(user)
                .attendanceStatus(AttendanceStatus.UNCHECKED)
                .build();
        return attendanceRepository.save(attendance);
    }
}
