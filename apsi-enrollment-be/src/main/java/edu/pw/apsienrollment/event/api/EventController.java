package edu.pw.apsienrollment.event.api;

import edu.pw.apsienrollment.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("all")
    ResponseEntity<List<EventDto>> findAll() {
        return ResponseEntity.ok(eventService
                .findAll()
                .stream()
                .map(EventDto::of)
                .collect(Collectors.toList()));
    }
}
