package edu.pw.apsienrollment.event.api.dto;

import edu.pw.apsienrollment.user.db.UserRole;
import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.event.db.EventType;

import java.util.Collection;
import java.util.Map;

@Value
@Builder
public class AllowedToCreateDto {
    Map<EventType, Collection<UserRole>> allowedToCreate;

    public static AllowedToCreateDto of(@NonNull Map<EventType, Collection<UserRole>> allowedToCreate) {
        return builder()
            .allowedToCreate(allowedToCreate)
            .build();
    }
}
