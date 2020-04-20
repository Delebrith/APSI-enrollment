package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventRepository;
import edu.pw.apsienrollment.event.specification.EventSpecification;
import edu.pw.apsienrollment.event.specification.EventSpecificationBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Page<Event> findAll(Integer page, Integer pageSize) {
        return eventRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Event> findAll(String searchQuery, Integer page, Integer pageSize) {
        return eventRepository.findAll(buildEventSpec(searchQuery), PageRequest.of(page, pageSize));
    }

    static public Specification<Event> buildEventSpec(@NonNull String searchQuery) {
        if(searchQuery == null) {
            throw new RuntimeException("Trying to build specification from an empty query");
        }
        EventSpecificationBuilder builder = new EventSpecificationBuilder();
        Pattern pattern = Pattern.compile("([\\w.]+?)([:<>])(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchQuery + ",");
        while(matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return builder.build();
    }
}
