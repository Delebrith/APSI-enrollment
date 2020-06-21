package edu.pw.apsienrollment.event.api.dto;

import edu.pw.apsienrollment.attendance.api.dto.AttendanceDto;
import edu.pw.apsienrollment.attendance.db.Attendance;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
public class AttendanceListForEventDto {
    Map<Long, List<AttendanceDto>> attendanceList;

    public static AttendanceListForEventDto of(@NonNull Map<Long, List<Attendance>> attendanceList) {
        return AttendanceListForEventDto.builder()
                .attendanceList(attendanceList.entrySet().stream()
                        .map(entry -> {
                            List<AttendanceDto> attendanceDtos = entry.getValue().stream()
                                    .map(AttendanceDto::of).collect(Collectors.toList());
                            return new HashMap.SimpleEntry<Long, List<AttendanceDto>>(entry.getKey(), attendanceDtos);
                        })
                        .collect(Collectors.toMap(HashMap.SimpleEntry::getKey, HashMap.SimpleEntry::getValue)))
                .build();
    }
}
