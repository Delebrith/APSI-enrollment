package edu.pw.apsienrollment.event.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import edu.pw.apsienrollment.event.EventService;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @ApiOperation(value = "Get list of events", nickname = "get list of events", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
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
