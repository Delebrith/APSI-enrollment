package edu.pw.apsienrollment.event.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.place.api.PlaceDto;
import edu.pw.apsienrollment.user.api.dto.UserDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MeetingDto {
    @NotNull Long id;
    String description;
    @NotNull LocalDateTime start;
    @NotNull LocalDateTime end;
    @NotNull List<UserDto> speakers;
    @NotNull PlaceDto place;

    public static MeetingDto of(Meeting meeting) {
        return builder()
            .id(meeting.getId())
            .description(meeting.getDescription())
            .start(meeting.getStart())
            .end(meeting.getEnd())
            .speakers(meeting.getSpeakers().stream().map(UserDto::of).collect(Collectors.toList()))
            .place(PlaceDto.of(meeting.getPlace()))
            .build();
    }
}