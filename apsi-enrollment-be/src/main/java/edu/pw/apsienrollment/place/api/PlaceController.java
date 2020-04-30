package edu.pw.apsienrollment.place.api;

import edu.pw.apsienrollment.common.api.dto.SearchRequestDTO;
import edu.pw.apsienrollment.place.PlaceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("place")
@RequiredArgsConstructor
@Validated
public class PlaceController {
    private final PlaceService placeService;

    @ApiOperation(value = "Get list of places", nickname = "get list of places", notes="",
            authorizations = {@Authorization(value = "JWT")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If valid credentials were provided", response = Iterable.class),
            @ApiResponse(code = 400, message = "If invalid data was provided")})
    @GetMapping
    ResponseEntity<Iterable<PlaceDto>> getPlaces(@Valid SearchRequestDTO request) {
        Iterable<PlaceDto> places = Optional.ofNullable(request.getSearchQuery())
                .map(query -> placeService.findPlaces(query, request.getPage(), request.getSize())
                        .map(PlaceDto::of))
                .orElse(placeService.findPlaces(request.getPage(), request.getSize())
                        .map(PlaceDto::of));
        return ResponseEntity.ok(places);
    }
}
