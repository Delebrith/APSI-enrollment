package edu.pw.apsienrollment.attendance.job;

import edu.pw.apsienrollment.event.meeting.Meeting;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

public class AttendanceCheckJobDetailFactory {
    public static JobDetail jobDetailFor(Meeting meeting) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(AttendanceCheckJob.MEETING_ID_KEY, meeting.getId());
        return JobBuilder.newJob(AttendanceCheckJob.class)
                .withIdentity(meeting.getId().toString())
                .setJobData(jobDataMap)
                .storeDurably()
                .withDescription("Attendance check job")
                .build();
    }

}
