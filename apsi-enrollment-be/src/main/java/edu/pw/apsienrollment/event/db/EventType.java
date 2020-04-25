package edu.pw.apsienrollment.event.db;

import edu.pw.apsienrollment.user.db.UserRole;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum EventType {
    LECTURE(UserRole.LECTURER),
    LABORATORY(UserRole.LECTURER),
    PROJECT(UserRole.LECTURER),
    SEMINAR(UserRole.SECRETARY),
    WORKSHOP(UserRole.LECTURER, UserRole.STUDENT),
    COURSE(UserRole.LECTURER, UserRole.STUDENT),
    CONFERENCE(UserRole.LECTURER, UserRole.STUDENT),
    MEETING(UserRole.LECTURER, UserRole.STUDENT);

    private final Set<UserRole> authorizedUserRoles;

    EventType(UserRole... roles) {
        this.authorizedUserRoles = Stream.of(roles).collect(Collectors.toSet());
    }
}
