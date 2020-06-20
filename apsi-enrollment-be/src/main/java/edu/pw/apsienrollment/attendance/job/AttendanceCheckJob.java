package edu.pw.apsienrollment.attendance.job;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.attendance.db.AttendanceRepository;
import edu.pw.apsienrollment.attendance.db.AttendanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobExecutionContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Slf4j
public class AttendanceCheckJob implements Job {
    public static final String MEETING_ID_KEY = "meeting_id";

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long meetingId = ((JobExecutionContextImpl) context).getMergedJobDataMap().getLongValue(MEETING_ID_KEY);
        boolean wasAttendanceChecked = attendanceRepository
                .existsByMeeting_IdAndAttendanceStatus(meetingId, AttendanceStatus.PRESENT);
        if (wasAttendanceChecked) {
            log.info("Attendance was checked for meeting {}. Marking absences...", meetingId);
            attendanceRepository
                    .findByMeeting_IdAndAndAttendanceStatus(meetingId, AttendanceStatus.UNCHECKED)
                    .forEach(this::markAsAbsent);
        } else {
            log.info("Attendance was not checked for meeting {}. Marking all as present...", meetingId);
            attendanceRepository
                    .findByMeeting_IdAndAndAttendanceStatus(meetingId, AttendanceStatus.UNCHECKED)
                    .forEach(this::markAsPresent);
        }
    }

    private void markAsAbsent(Attendance attendance) {
        attendance.setAttendanceStatus(AttendanceStatus.ABSENT);
        attendanceRepository.save(attendance);
    }

    private void markAsPresent(Attendance attendance) {
        attendance.setAttendanceStatus(AttendanceStatus.PRESENT);
        attendanceRepository.save(attendance);
    }
}
