package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Collection<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Page<Event> findAll(Integer page, Integer pageSize) {
        if (page < 0) throw new RuntimeException("ValueError: Page cannot be negative");
        if (pageSize <= 0) throw new RuntimeException(("ValueError: Page size should be greater than 0"));
        return eventRepository.findAll(PageRequest.of(page, pageSize));
    }
}
