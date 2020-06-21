package edu.pw.apsienrollment.event.api.dto;

import edu.pw.apsienrollment.attendance.api.dto.AttendanceDto;
import edu.pw.apsienrollment.attendance.db.Attendance;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
public class AttendanceListDto {
    Map<Long, List<AttendanceDto>> attendanceList;

    public static AttendanceListDto of(@NonNull Map<Long, List<Attendance>> attendanceList) {
        return AttendanceListDto.builder()
                .attendanceList(attendanceList.entrySet().stream()
                        .map(entry -> Map.entry(
                                entry.getKey(),
                                entry.getValue()
                                        .stream()
                                        .map(AttendanceDto::of)
                                        .collect(Collectors.toList()))
                        )
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .build();
    }
}
