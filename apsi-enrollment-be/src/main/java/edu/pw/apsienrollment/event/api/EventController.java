package edu.pw.apsienrollment.event.api;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import edu.pw.apsienrollment.event.EventService;
import edu.pw.apsienrollment.event.db.Event;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    ResponseEntity<Iterable<EventDto>> events(EventRequestDTO request) {
        if (request.getSearchQuery() != null) {
            return search(request.getSearchQuery(), request.getPage(), request.getSize());
        }
        return findAll(request.getPage(), request.getSize());
    }

    private ResponseEntity<Iterable<EventDto>> findAll(Integer page, Integer pageSize) {
        return ResponseEntity.ok(eventService.findAll(page, pageSize)
                .map(EventDto::of));
    }

    private ResponseEntity<Iterable<EventDto>> search(String searchQuery, Integer page, Integer size) {
        return ResponseEntity.ok(eventService.findAll(searchQuery, page, size)
                .map(EventDto::of));
    }
}
