package edu.pw.apsienrollment.event.meeting;

import edu.pw.apsienrollment.event.api.dto.MeetingRequestDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.exception.EventNotFoundException;
import edu.pw.apsienrollment.event.meeting.exception.MeetingNotFoundException;
import edu.pw.apsienrollment.event.meeting.exception.PlaceNotMeetingRequirementsException;
import edu.pw.apsienrollment.event.meeting.exception.SpeakerUnavailableException;
import edu.pw.apsienrollment.place.PlaceService;
import edu.pw.apsienrollment.place.db.Place;
import edu.pw.apsienrollment.user.UserService;
import edu.pw.apsienrollment.user.db.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final UserService userService;
    private final PlaceService placeService;


    @Override
    public Meeting getById(Long id) {
        return meetingRepository.findById(id).orElseThrow(MeetingNotFoundException::new);
    }
    
    @Override
    public Meeting createMeeting(Event event, MeetingRequestDto dto) {
        Place place = getPlace(event, dto);
        Meeting meeting =  Meeting.builder()
                .description(dto.getDescription())
                .place(place)
                .start(dto.getStart())
                .end(dto.getEnd())
                .speakers(dto.getSpeakerIds()
                        .stream()
                        .map(userId -> getSpeaker(dto, userId))
                        .collect(Collectors.toSet())
                )
                .event(event)
                .build();
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getMeetings(Event event) {
        return meetingRepository.findByEvent(event);
    }

    private User getSpeaker(MeetingRequestDto dto, Integer userId) {
        return userService
                .getUserIfAvailable(userId, dto.getStart(), dto.getEnd())
                .orElseThrow(() ->
                        new SpeakerUnavailableException(dto.getStart(), dto.getEnd())
                );
    }

    private Place getPlace(Event event, MeetingRequestDto dto) {
        Place place = placeService
                .getPlaceIfAvailable(
                        dto.getPlaceId(), dto.getStart(), dto.getEnd())
                .orElseThrow(() ->
                        new PlaceNotMeetingRequirementsException(dto.getStart(), dto.getEnd())
                );
        if (event.getAttendeesLimit() != null && place.getCapacity() < event.getAttendeesLimit()) {
            throw new PlaceNotMeetingRequirementsException(place.getName(), place.getCapacity(), event.getAttendeesLimit());
        }
        return place;
    }
}
