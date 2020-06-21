package edu.pw.apsienrollment.attendance.api.dto;

import edu.pw.apsienrollment.attendance.api.dto.AttendanceDto;
import edu.pw.apsienrollment.attendance.db.Attendance;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class AttendanceListForMeetingDto {
    List<AttendanceDto> attendanceList;

    public static AttendanceListForMeetingDto of(@NonNull List<Attendance> attendanceList) {
        return AttendanceListForMeetingDto.builder()
                .attendanceList(attendanceList.stream().map(AttendanceDto::of).collect(Collectors.toList()))
                .build();
    }
}
