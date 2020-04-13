package edu.pw.apsienrollment.event.api;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventType;
import edu.pw.apsienrollment.user.api.UserDto;


@Value
@Builder
public class EventDto {
    Long id;
    String name;
    String description;
    EventType eventType;
    Integer attendeesLimit;
    UserDto organizer;

    public static EventDto of(@NonNull Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventType(event.getEventType())
                .attendeesLimit(event.getAttendeesLimit())
                .organizer(UserDto.of(event.getOrganizer()))
                .build();
    }
}
