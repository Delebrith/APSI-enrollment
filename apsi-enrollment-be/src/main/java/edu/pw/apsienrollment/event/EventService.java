package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.specification.EventSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface EventService {
    Page<Event> findAll(Integer page, Integer pageSize);
    Page<Event> findAll(String searchQuery, Integer page, Integer pageSize);
}
