package edu.pw.apsienrollment.event.api.dto;

import edu.pw.apsienrollment.event.db.EventType;
import lombok.Value;

import javax.validation.constraints.*;
import java.util.Set;

@Value
public class EventRequestDto {
    @NotBlank String name;

    String description;

    @NotNull EventType eventType;

    @NotNull @Min(1) Integer attendeesLimit;

    @NotEmpty Set<MeetingRequestDto> meetings;

    @PositiveOrZero Float cost = 0f;
}
