package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.common.db.SearchQueryParser;
import edu.pw.apsienrollment.event.api.dto.EventRequestDto;
import edu.pw.apsienrollment.event.api.dto.MeetingRequestDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventRepository;
import edu.pw.apsienrollment.event.db.EventSpecification;
import edu.pw.apsienrollment.event.db.EventType;
import edu.pw.apsienrollment.event.exception.EventNotFoundException;
import edu.pw.apsienrollment.event.exception.UserUnauthorizedToCreateEventException;
import edu.pw.apsienrollment.event.meeting.MeetingService;
import edu.pw.apsienrollment.user.db.User;
import edu.pw.apsienrollment.user.db.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class EventServiceImpl implements EventService {
    private final AuthenticationService authenticationService;
    private final MeetingService meetingService;
    private final EventRepository eventRepository;

    @Override
    public Page<Event> findAll(Integer page, Integer pageSize) {
        return eventRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Event> findAll(String searchQuery, Integer page, Integer pageSize) {
        return eventRepository.findAll(
                new EventSpecification(SearchQueryParser.parse(searchQuery)),
                PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Event> findOfAuthenticatedUser(Integer page, Integer pageSize) {
        User user = authenticationService.getAuthenticatedUser();
        return eventRepository.findAll(
                new EventSpecification(Collections.emptyList()).toSpecificationWithSpeaker(user),
                PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Event> findOfAuthenticatedUser(String searchQuery, Integer page, Integer pageSize) {
        User user = authenticationService.getAuthenticatedUser();
        return eventRepository.findAll(
                new EventSpecification(SearchQueryParser.parse(searchQuery)).toSpecificationWithSpeaker(user),
                PageRequest.of(page, pageSize));
    }

    @Override
    public Event createEvent(EventRequestDto eventRequestDto) {
        User creator = authenticationService.getAuthenticatedUser();
        validateEventCreationAuthorities(eventRequestDto, creator);

        Event created = Event.builder()
                .name(eventRequestDto.getName())
                .description(eventRequestDto.getDescription())
                .eventType(eventRequestDto.getEventType())
                .attendeesLimit(eventRequestDto.getAttendeesLimit())
                .organizer(authenticationService.getAuthenticatedUser())
                .cost(eventRequestDto.getCost())
                .build();
        eventRepository.save(created);
        createMeetings(eventRequestDto.getMeetings(), created);

        return created;
    }

    private void validateEventCreationAuthorities(EventRequestDto eventRequestDto, User creator) {
        eventRequestDto.getEventType()
                .getAuthorizedUserRoles().stream()
                .filter(role -> creator.getRoles().contains(role))
                .findAny()
                .orElseThrow(() -> new UserUnauthorizedToCreateEventException(eventRequestDto.getEventType()));
    }

    private void createMeetings(Collection<MeetingRequestDto> meetingRequestDtos, Event created) {
        meetingRequestDtos.forEach(dto -> meetingService.createMeeting(created, dto));
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    @Override
    public Map<EventType, Collection<UserRole>> getAllowedToCreate() {
        return Arrays.stream(EventType.values())
                .collect(Collectors.toMap(
                        type -> type,
                        type -> type.getAuthorizedUserRoles()));
    }

}
