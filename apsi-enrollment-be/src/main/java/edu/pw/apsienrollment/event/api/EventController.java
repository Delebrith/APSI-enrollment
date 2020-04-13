package edu.pw.apsienrollment.event.api;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import edu.pw.apsienrollment.event.EventService;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.specification.EventSpecificationBuilder;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("")
    ResponseEntity<Iterable<EventDto>> events(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> search) {
        if(search.isPresent()) {
            if(checkPageParams(page, size)) {
                return this.search(search.get(), page.get(), size.get());
            }
            return this.search(search.get());
        }
        if(checkPageParams(page, size)) {
            return findAll(page.get(), size.get());
        }
        return findAll();
    }

    static Boolean checkPageParams(Optional<Integer> page, Optional<Integer> size) {
        if(page.isPresent() || size.isPresent()) {
            if(page.isPresent() && size.isPresent()) {
                if(page.get() < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot fetch negative page");
                }
                if(size.get() < 1) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page size cannot be less than 1");
                }
                return true;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page and Size have to be both specified");
        }
        return false;
    }

    ResponseEntity<Iterable<EventDto>> findAll() {
        return ResponseEntity.ok(eventService.findAll()
                .stream()
                .map(EventDto::of)
                .collect(Collectors.toList()));
    }

    ResponseEntity<Iterable<EventDto>> findAll(Integer page, Integer pageSize) {
        return ResponseEntity.ok(eventService.findAll(page, pageSize)
                .map(EventDto::of));
    }

    ResponseEntity<Iterable<EventDto>> search(String query) {
        Specification<Event> spec = eventSpecFrom(query);
        return ResponseEntity.ok(eventService.findAll(spec)
                .stream()
                .map(EventDto::of)
                .collect(Collectors.toList()));
    }

    ResponseEntity<Iterable<EventDto>> search(String query, Integer page, Integer size) {
        Specification<Event> spec = eventSpecFrom(query);
        System.out.println("Searching");
        System.out.println("Query is " + query);
        return ResponseEntity.ok(eventService.findAll(spec, page, size)
                .map(EventDto::of));
    }

    static Specification<Event> eventSpecFrom(@NonNull String query) {
        EventSpecificationBuilder builder = new EventSpecificationBuilder();
        Pattern pattern = Pattern.compile("([\\w]+?)([:<>])(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(query + ",");
        while(matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return builder.build();
    }
}
