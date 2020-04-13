package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventRepository;
import edu.pw.apsienrollment.event.specification.EventSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        checkPagingParameters(page, pageSize);
        return eventRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Collection<Event> findAll(Specification<Event> spec) {
        return eventRepository.findAll(spec);
    }

    @Override
    public Page<Event> findAll(Specification<Event> spec, Integer page, Integer pageSize) {
        checkPagingParameters(page, pageSize);
        return eventRepository.findAll(spec, PageRequest.of(page, pageSize));
    }

    static void checkPagingParameters(Integer page, Integer pageSize) {
        if (page == null) throw new RuntimeException("ValueError: Page cannot be null");
        if (pageSize == null) throw new RuntimeException("ValueError: Page size cannot be null");
        if (page < 0) throw new RuntimeException("ValueError: Page cannot be negative");
        if (pageSize <= 0) throw new RuntimeException(("ValueError: Page size should be greater than 0"));
    }
}
