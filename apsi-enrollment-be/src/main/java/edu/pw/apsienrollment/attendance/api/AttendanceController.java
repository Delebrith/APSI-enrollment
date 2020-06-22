package edu.pw.apsienrollment.attendance.api;

import javax.validation.Valid;

import edu.pw.apsienrollment.attendance.api.dto.AttendanceListForMeetingDto;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.event.meeting.MeetingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pw.apsienrollment.event.EventService;
import edu.pw.apsienrollment.event.api.dto.AttendanceListForEventDto;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.attendance.AttendanceService;
import edu.pw.apsienrollment.attendance.api.dto.AttendanceDto;
import edu.pw.apsienrollment.common.api.dto.PageRequestDTO;
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
    private final EventService eventService;
    private final MeetingService meetingService;

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

    
    @ApiOperation(value = "Get attendance QR code", nickname = "get attendance QR code", notes = "",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping(value = "{id}/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getQrCode(id));
    }

    @ApiOperation(value = "Check attendance QR code validity and mark user as present",
            nickname = "check attendance QR code", notes = "",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @PostMapping("{id}/mark-as-present")
    ResponseEntity<Void> markAsPresent(@PathVariable Long id,
                                       @RequestParam String token,
                                       @RequestParam Long meetingId) {
        attendanceService.markAsPresent(id, token, meetingId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get attendance list for meeting", nickname = "get attendance list for meeting", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided"),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("meeting/{id}")
    ResponseEntity<AttendanceListForMeetingDto> getAttendanceListForMeeting(@PathVariable("id") Long id) {
        Meeting meeting = meetingService.getById(id);
        return ResponseEntity.ok(AttendanceListForMeetingDto.of(attendanceService.getAttendanceListForMeeting(meeting)));
    }

    @ApiOperation(value = "Get attendance lists for event", nickname = "get attendance lists for event", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided"),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("event/{id}")
    ResponseEntity<AttendanceListForEventDto> getAttendanceListForEvent(@PathVariable("id") Long id) {
        Event event = eventService.getById(id);
        return ResponseEntity.ok(AttendanceListForEventDto.of(attendanceService.getAttendanceListForEvent(event)));
    }
}
