package edu.pw.apsienrollment.event.api.dto;

import edu.pw.apsienrollment.event.db.EventType;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
public class EventRequestDto {
    @NotBlank String name;

    String description;

    @NotNull EventType eventType;

    @NotNull @Min(1) Integer attendeesLimit;

    @NotEmpty Set<MeetingRequestDto> meetings;

    Float cost = 0f;
}
