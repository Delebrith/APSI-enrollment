package edu.pw.apsienrollment.attendance.api.dto;

import javax.validation.constraints.NotNull;

import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.attendance.db.AttendanceStatus;
import edu.pw.apsienrollment.event.api.dto.MeetingDto;
import edu.pw.apsienrollment.user.api.dto.UserDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttendanceDto {
    @NotNull Long id;
    @NotNull MeetingDto meeting;
    @NotNull UserDto user;
    @NotNull AttendanceStatus status;

    public static AttendanceDto of(Attendance attendance) {
        return builder()
                .id(attendance.getId())
                .meeting(MeetingDto.of(attendance.getMeeting()))
                .user(UserDto.of(attendance.getUser()))
                .status(attendance.getAttendanceStatus())
                .build();
    }
}