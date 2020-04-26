package edu.pw.apsienrollment.event.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.Meeting;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventWithMeetingsDto {
    @NotNull EventDto event;
    @NotNull List<MeetingDto> meetings;

    public static EventWithMeetingsDto of(Event event, List<Meeting> meetings) {
        return builder()
            .event(EventDto.of(event))
            .meetings(meetings.stream().map(MeetingDto::of).collect(Collectors.toList()))
            .build();
    }
}