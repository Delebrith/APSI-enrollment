package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.event.api.dto.EventRequestDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.pw.apsienrollment.user.db.UserRole;
import org.springframework.data.domain.Page;

public interface EventService {
    Page<Event> findAll(Integer page, Integer pageSize);
    Page<Event> findAll(String searchQuery, Integer page, Integer pageSize);

    Page<Event> findOfAuthenticatedUserBySpeaker(Integer page, Integer pageSize);
    Page<Event> findOfAuthenticatedUserBySpeaker(String searchQuery, Integer page, Integer pageSize);

    Page<Event> findOfAuthenticatedUserByOrganizer(Integer page, Integer pageSize);
    Page<Event> findOfAuthenticatedUserByOrganizer(String searchQuery, Integer page, Integer pageSize);
    
    Page<Event> findOfAuthenticatedUserByEnrollment(Integer page, Integer pageSize);
    Page<Event> findOfAuthenticatedUserByEnrollment(String searchQuery, Integer page, Integer pageSize);

    Event getById(Long id);

    Event createEvent(EventRequestDto eventRequestDto);

    Map<EventType, Collection<UserRole>> getAllowedToCreate();

    Map<Long, List<Attendance>> getAttendanceList(Event event);
}
