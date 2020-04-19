package edu.pw.apsienrollment.event.api;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventType;
import edu.pw.apsienrollment.user.api.UserDto;
import org.hibernate.annotations.Formula;


@Value
@Builder
public class EventDto {
    Long id;
    String name;
    String description;
    EventType eventType;
    Integer attendeesLimit;
    UserDto organizer;

    @Formula("(select min(m.start) from meeting as m where m.event_id = id)")
    Timestamp start;
    @Formula("(select max(m.end) from meeting as m where m.event_id = id)")
    Timestamp end;

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
