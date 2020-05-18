package edu.pw.apsienrollment.enrollment.db;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.user.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Long countByEvent(Event event);

    boolean existsByUserAndEvent(User user, Event event);

    Collection<Enrollment> findByUser(User user);
}
