package edu.pw.apsienrollment.event.api.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public class MeetingRequestDto {
    @NotBlank String description;

    @NotNull LocalDateTime start;

    @NotNull LocalDateTime end;

    @NotEmpty Set<Integer> speakerIds;

    @NotNull Long placeId;
}
