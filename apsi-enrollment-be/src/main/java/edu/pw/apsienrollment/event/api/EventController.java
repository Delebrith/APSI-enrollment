package edu.pw.apsienrollment.event.api;

import edu.pw.apsienrollment.common.api.dto.SearchRequestDTO;
import edu.pw.apsienrollment.event.EventService;
import edu.pw.apsienrollment.event.api.dto.AllowedToCreateDto;
import edu.pw.apsienrollment.event.api.dto.EventDto;
import edu.pw.apsienrollment.event.api.dto.EventRequestDto;
import edu.pw.apsienrollment.event.api.dto.EventWithMeetingsDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.meeting.MeetingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;
    private final MeetingService meetingService;

    @ApiOperation(value = "Get list of events", nickname = "get list of events", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping
    ResponseEntity<Iterable<EventDto>> getEvents(@Valid SearchRequestDTO request) {
        if (request.getSearchQuery() != null) {
            return search(request.getSearchQuery(), request.getPage(), request.getSize());
        }
        return findAll(request.getPage(), request.getSize());
    }

    @ApiOperation(value = "Create event", nickname = "create event", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @PostMapping
    ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        EventDto created = EventDto.of(eventService.createEvent(eventRequestDto));
        return ResponseEntity
                .created(URI.create(String.format("/events/%d", created.getId())))
                .body(created);
    }

    private ResponseEntity<Iterable<EventDto>> findAll(Integer page, Integer pageSize) {
        return ResponseEntity.ok(eventService.findAll(page, pageSize)
                .map(EventDto::of));
    }

    private ResponseEntity<Iterable<EventDto>> search(String searchQuery, Integer page, Integer size) {
        return ResponseEntity.ok(eventService.findAll(searchQuery, page, size)
                .map(EventDto::of));
    }

    @ApiOperation(value = "Get event", nickname = "get event", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 404, message = "If event does not exist")})
    @GetMapping("{id}")
    ResponseEntity<EventWithMeetingsDto> event(@PathVariable("id") Long id) {
        Event event = eventService.getById(id);
        return ResponseEntity.ok(EventWithMeetingsDto.of(event, meetingService.getMeetings(event)));
    }


    @ApiOperation(value = "Get allowed to create", nickname = "get allowed to create", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided"),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("allowed-to-create")
    ResponseEntity<AllowedToCreateDto> getAllowedToCreate() {
        return ResponseEntity.ok(AllowedToCreateDto.of(eventService.getAllowedToCreate()));
    }
    
    @ApiOperation(value = "Get list of events where authenticated user is a speaker",
        nickname = "get list of my events", notes="", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("my-speaker")
    ResponseEntity<Iterable<EventDto>> getMyEventsBySpeaker(@Valid SearchRequestDTO request) {
        if (request.getSearchQuery() != null) {
            return searchMyBySpeaker(request.getSearchQuery(), request.getPage(), request.getSize());
        }
        return findAllMyBySpeaker(request.getPage(), request.getSize());
    }
    
    private ResponseEntity<Iterable<EventDto>> findAllMyBySpeaker(Integer page, Integer pageSize) {
        return ResponseEntity.ok(eventService.findOfAuthenticatedUserBySpeaker(page, pageSize)
                .map(EventDto::of));
    }

    private ResponseEntity<Iterable<EventDto>> searchMyBySpeaker(String searchQuery, Integer page, Integer size) {
        return ResponseEntity.ok(eventService.findOfAuthenticatedUserBySpeaker(searchQuery, page, size)
                .map(EventDto::of));
    }
    
    @ApiOperation(value = "Get list of events where authenticated user is the organizer",
        nickname = "get list of my organized events", notes="", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("my-organizer")
    ResponseEntity<Iterable<EventDto>> getMyEventsByOrganizer(@Valid SearchRequestDTO request) {
        if (request.getSearchQuery() != null) {
            return searchMyByOrganizer(request.getSearchQuery(), request.getPage(), request.getSize());
        }
        return findAllMyByOrganizer(request.getPage(), request.getSize());
    }
    
    private ResponseEntity<Iterable<EventDto>> findAllMyByOrganizer(Integer page, Integer pageSize) {
        return ResponseEntity.ok(eventService.findOfAuthenticatedUserByOrganizer(page, pageSize)
                .map(EventDto::of));
    }

    private ResponseEntity<Iterable<EventDto>> searchMyByOrganizer(String searchQuery, Integer page, Integer size) {
        return ResponseEntity.ok(eventService.findOfAuthenticatedUserByOrganizer(searchQuery, page, size)
                .map(EventDto::of));
    }

}
