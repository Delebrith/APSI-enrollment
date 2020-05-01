package edu.pw.apsienrollment.event.meeting;

import java.util.List;

import edu.pw.apsienrollment.event.db.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByEvent(Event event);
}
