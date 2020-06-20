package edu.pw.apsienrollment.attendance.job;

import edu.pw.apsienrollment.event.meeting.Meeting;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.sql.Timestamp;

public class AttendanceCheckTriggerFactory {
    public static Trigger triggerFor(Meeting meeting, JobDetail job) {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .startAt(Timestamp.valueOf(meeting.getEnd().plusHours(1)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();
    }
}
