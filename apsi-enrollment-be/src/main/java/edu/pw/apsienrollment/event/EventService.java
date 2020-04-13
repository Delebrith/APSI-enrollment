package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.db.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface EventService {

    Collection<Event> findAll();
    Page<Event> findAll(Integer page, Integer pageSize);

}
