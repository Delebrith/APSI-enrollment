package edu.pw.apsienrollment.attendance.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pw.apsienrollment.attendance.AttendanceService;
import edu.pw.apsienrollment.attendance.api.dto.AttendanceDto;
import edu.pw.apsienrollment.common.api.dto.PageRequestDTO;
import edu.pw.apsienrollment.event.api.dto.MeetingDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("attendance")
@RequiredArgsConstructor
@Validated
public class AttendanceController {
    private final AttendanceService attendanceService;
    
    @ApiOperation(value = "Get list of attendance of the authenticated user",
        nickname = "get list of my attendance", notes="", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("my")
    ResponseEntity<Iterable<AttendanceDto>> getMyAttendance(@Valid PageRequestDTO request) {
        
        return ResponseEntity.ok(attendanceService.getMeetingsOfAuthorizedUser(request.getPage(), request.getSize())
            .map(AttendanceDto::of));
    }
}