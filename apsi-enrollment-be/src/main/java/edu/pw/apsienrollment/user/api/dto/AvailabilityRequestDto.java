package edu.pw.apsienrollment.user.api.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

@Value
public class AvailabilityRequestDto {
    @NotNull @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromTime;

    @NotNull @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toTime;
}
