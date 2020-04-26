package edu.pw.apsienrollment.event.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventType;
import edu.pw.apsienrollment.user.api.dto.UserDto;


@Value
@Builder
public class EventDto {
    Long id;
    String name;
    String description;
    EventType eventType;
    Integer attendeesLimit;
    LocalDateTime start;
    LocalDateTime end;
    UserDto organizer;

    public static EventDto of(@NonNull Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventType(event.getEventType())
                .attendeesLimit(event.getAttendeesLimit())
                .start(event.getStart())
                .end(event.getEnd())
                .organizer(UserDto.of(event.getOrganizer()))
                .build();
    }
}
