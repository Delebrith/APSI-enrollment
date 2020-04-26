package edu.pw.apsienrollment.user.api.dto;

import edu.pw.apsienrollment.user.db.User;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class AvailabilityResultDto {
    Boolean available;

    public static AvailabilityResultDto of(Optional<User> user) {
        return AvailabilityResultDto.builder()
                .available(user.isPresent())
                .build();
    }
}
