package edu.pw.apsienrollment.enrollment.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.user.db.User;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Long countByEvent(Event event);

    Long countByUserAndEvent(User user, Event event);
}
