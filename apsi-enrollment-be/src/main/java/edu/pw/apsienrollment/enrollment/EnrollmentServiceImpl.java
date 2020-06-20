package edu.pw.apsienrollment.enrollment;

import edu.pw.apsienrollment.attendance.AttendanceService;
import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.enrollment.db.Enrollment;
import edu.pw.apsienrollment.enrollment.db.EnrollmentRepository;
import edu.pw.apsienrollment.enrollment.db.EnrollmentStatus;
import edu.pw.apsienrollment.enrollment.exception.AlreadySignedUpException;
import edu.pw.apsienrollment.enrollment.exception.AttendeesLimitException;
import edu.pw.apsienrollment.enrollment.exception.EnrollmentNotFoundException;
import edu.pw.apsienrollment.event.EventService;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.meeting.MeetingService;
import edu.pw.apsienrollment.user.db.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final AuthenticationService authenticationService;
    private final EventService eventService;
    private final MeetingService meetingService;
    private final AttendanceService attendanceService;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment signUp(Long eventId) {
        User user = authenticationService.getAuthenticatedUser();
        Event event = eventService.getById(eventId);

        if (isAlreadySigned(user, event)) {
            throw new AlreadySignedUpException();
        }
        if (event.getAttendeesLimit() != null && isEventFull(event)) {
            throw new AttendeesLimitException();
        }

        EnrollmentStatus status =
            (event.getCost() > 0f) ? EnrollmentStatus.PENDING
                                   : EnrollmentStatus.ENROLLED;

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .event(event)
                .status(status)
                .build();
        enrollmentRepository.save(enrollment);

        if (status == EnrollmentStatus.ENROLLED) {
            meetingService.getMeetings(event)
                    .forEach(meeting -> attendanceService.putIntoAttendanceList(meeting, user));
        }

        return enrollment;
    }

    @Override
    public Collection<Enrollment> getMyEnrollments() {
        return enrollmentRepository.findByUser(authenticationService.getAuthenticatedUser());
    }

    private boolean isEventFull(Event event) {
        return enrollmentRepository.countByEvent(event) >= event.getAttendeesLimit();
    }

    private boolean isAlreadySigned(User user, Event event) {
        return enrollmentRepository.existsByUserAndEvent(user, event);
    }

    public void enroll(Enrollment enrollment) {
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        meetingService.getMeetings(enrollment.getEvent())
                .forEach(meeting -> attendanceService.putIntoAttendanceList(meeting, enrollment.getUser()));
        enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFoundException::new);
    }
}
