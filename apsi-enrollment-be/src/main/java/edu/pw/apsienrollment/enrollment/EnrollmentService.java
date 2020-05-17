package edu.pw.apsienrollment.enrollment;

import edu.pw.apsienrollment.enrollment.api.dto.EnrollmentRequestDto;
import edu.pw.apsienrollment.enrollment.db.Enrollment;

public interface EnrollmentService {
    Enrollment signUp(Long eventId);
}
