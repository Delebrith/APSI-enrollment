package edu.pw.apsienrollment.attendance;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

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
                                       @RequestParam String token) {
        attendanceService.markAsPresent(id, token);
        return ResponseEntity.ok().build();
    }

}
