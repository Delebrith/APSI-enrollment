package edu.pw.apsienrollment.user.api;

import edu.pw.apsienrollment.authentication.api.dto.AuthenticatedUserDto;
import edu.pw.apsienrollment.common.api.dto.SearchRequestDTO;
import edu.pw.apsienrollment.place.api.PlaceDto;
import edu.pw.apsienrollment.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Get user info", nickname = "get user info", notes = "",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = AuthenticatedUserDto.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping("{id}")
    ResponseEntity<AuthenticatedUserDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(AuthenticatedUserDto.of(userService.getUser(id)));
    }

    @ApiOperation(value = "Get list of places", nickname = "get list of places", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping
    ResponseEntity<Iterable<AuthenticatedUserDto>> getUsers(@Valid SearchRequestDTO request) {
        Iterable<AuthenticatedUserDto> users = Optional.ofNullable(request.getSearchQuery())
                .map(query -> userService.findUsers(query, request.getPage(), request.getSize())
                        .map(AuthenticatedUserDto::of))
                .orElse(userService.findUsers(request.getPage(), request.getSize())
                        .map(AuthenticatedUserDto::of));
        return ResponseEntity.ok(users);
    }
}
