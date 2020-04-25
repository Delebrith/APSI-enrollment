package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.api.dto.EventRequestDto;
import edu.pw.apsienrollment.event.db.Event;
import org.springframework.data.domain.Page;

public interface EventService {
    Page<Event> findAll(Integer page, Integer pageSize);
    Page<Event> findAll(String searchQuery, Integer page, Integer pageSize);

    Event createEvent(EventRequestDto eventRequestDto);
}
