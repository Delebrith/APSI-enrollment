package edu.pw.apsienrollment.enrollment.api;

import edu.pw.apsienrollment.enrollment.EnrollmentService;
import edu.pw.apsienrollment.enrollment.api.dto.EnrollmentDto;
import edu.pw.apsienrollment.enrollment.api.dto.EnrollmentRequestDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("enrollment")
@RequiredArgsConstructor
@Validated
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @ApiOperation(value = "Sign up", nickname = "sign up", notes="",
        authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @PostMapping
    ResponseEntity<EnrollmentDto> signUp(@Valid @RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        EnrollmentDto enrollment = EnrollmentDto.of(enrollmentService.signUp(enrollmentRequestDto.getEventId()));
        return ResponseEntity.ok(enrollment);
    }

    @ApiOperation(value = "Get my enrollments", nickname = "my enrollments", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("my-enrollments")
    ResponseEntity<Iterable<EnrollmentDto>> getMyEnrollments() {
        Iterable<EnrollmentDto> enrollments = enrollmentService.getMyEnrollments()
                .stream()
                .map(EnrollmentDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollments);
    }
}
