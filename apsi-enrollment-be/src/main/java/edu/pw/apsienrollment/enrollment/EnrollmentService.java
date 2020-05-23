package edu.pw.apsienrollment.enrollment;

import edu.pw.apsienrollment.enrollment.db.Enrollment;

import java.util.Collection;

public interface EnrollmentService {
    Enrollment signUp(Long eventId);

    Collection<Enrollment> getMyEnrollments();

    void enroll(Enrollment enrollment);

    Enrollment getById(Long id);
}
