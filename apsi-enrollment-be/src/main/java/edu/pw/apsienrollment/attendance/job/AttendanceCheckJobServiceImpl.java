package edu.pw.apsienrollment.attendance.job;

import edu.pw.apsienrollment.event.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class AttendanceCheckJobServiceImpl implements AttendanceCheckJobService {
    private final Scheduler scheduler;
    @Override
    public void scheduleJob(Meeting meeting) {
        try {
            JobDetail job = AttendanceCheckJobDetailFactory.jobDetailFor(meeting);
            scheduler.scheduleJob(job, AttendanceCheckTriggerFactory.triggerFor(meeting, job));
            log.info("Scheduled attendance check for meeting {}", meeting);
        } catch (SchedulerException e) {
            log.error(String.format(
                    "Could not create attendance check job for meeting %s", meeting), e);
        }
    }
}
