package edu.pw.apsienrollment.attendance.job;

import edu.pw.apsienrollment.event.meeting.Meeting;

public interface AttendanceCheckJobService {
    void scheduleJob(Meeting meeting);
}
