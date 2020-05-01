package edu.pw.apsienrollment.event;

import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.common.db.SearchQueryParser;
import edu.pw.apsienrollment.event.api.dto.EventRequestDto;
import edu.pw.apsienrollment.event.api.dto.MeetingRequestDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventRepository;
import edu.pw.apsienrollment.event.db.Meeting;
import edu.pw.apsienrollment.event.db.MeetingRepository;
import edu.pw.apsienrollment.event.exception.PlaceNotMeetingRequirementsException;
import edu.pw.apsienrollment.event.exception.SpeakerUnavailableException;
import edu.pw.apsienrollment.event.exception.UserUnauthorizedToCreateEventException;
import edu.pw.apsienrollment.place.PlaceService;
import edu.pw.apsienrollment.event.db.specification.EventSpecification;
import edu.pw.apsienrollment.place.db.Place;
import edu.pw.apsienrollment.user.UserService;
import edu.pw.apsienrollment.user.db.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final AuthenticationService authenticationService;
    private final PlaceService placeService;
    private final UserService userService;

    private final EventRepository eventRepository;
    private final MeetingRepository meetingRepository;

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
                .orElseThrow(UserUnauthorizedToCreateEventException::new);
    }

    private void createMeetings(Collection<MeetingRequestDto> meetingRequestDtos, Event created) {
        meetingRequestDtos.stream()
                .map(meetingRequestDto -> buildMeeting(created, meetingRequestDto))
                .forEach(meetingRepository::save);
    }

    private Meeting buildMeeting(Event created, MeetingRequestDto meetingRequestDto) {
        Place place = getPlace(created, meetingRequestDto);
        return Meeting.builder()
                .description(meetingRequestDto.getDescription())
                .place(place)
                .start(meetingRequestDto.getStart())
                .end(meetingRequestDto.getEnd())
                .speakers(meetingRequestDto.getSpeakerIds()
                        .stream()
                        .map(userId -> getSpeaker(meetingRequestDto, userId))
                        .collect(Collectors.toSet())
                )
                .event(created)
                .build();
    }

    private User getSpeaker(MeetingRequestDto meetingRequestDto, Integer userId) {
        return userService
                .getUserIfAvailable(userId, meetingRequestDto.getStart(), meetingRequestDto.getEnd())
                .orElseThrow(() ->
                        new SpeakerUnavailableException(meetingRequestDto.getStart(), meetingRequestDto.getEnd())
                );
    }

    private Place getPlace(Event created, MeetingRequestDto meetingRequestDto) {
        Place place = placeService
                .getPlaceIfAvailable(
                        meetingRequestDto.getPlaceId(), meetingRequestDto.getStart(), meetingRequestDto.getEnd())
                .orElseThrow(() ->
                        new PlaceNotMeetingRequirementsException(meetingRequestDto.getStart(), meetingRequestDto.getEnd())
                );
        if (created.getAttendeesLimit() != null && place.getCapacity() < created.getAttendeesLimit()) {
            throw new PlaceNotMeetingRequirementsException(place.getName(), place.getCapacity(), created.getAttendeesLimit());
        }
        return place;
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    @Override
    public List<Meeting> getMeetings(Event event) {
        return meetingRepository.findByEvent(event);
    }
}
