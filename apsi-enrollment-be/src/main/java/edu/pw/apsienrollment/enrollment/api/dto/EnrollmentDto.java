package edu.pw.apsienrollment.enrollment.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import edu.pw.apsienrollment.enrollment.db.EnrollmentStatus;
import edu.pw.apsienrollment.event.api.dto.EventDto;
import edu.pw.apsienrollment.user.api.dto.UserDto;

@Value
@Builder
public class EnrollmentDto {
    Long id;
    UserDto user;
    EventDto event;
    EnrollmentStatus status;

    public static EnrollmentDto of(@NonNull Enrollment enrollment) {
        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .user(UserDto.of(enrollment.getUser()))
                .event(EventDto.of(enrollment.getEvent()))
                .status(enrollment.getStatus())
                .build();
    }
}
