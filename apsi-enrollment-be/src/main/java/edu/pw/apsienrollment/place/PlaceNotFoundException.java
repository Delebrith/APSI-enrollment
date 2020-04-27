package edu.pw.apsienrollment.place;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlaceNotFoundException extends RuntimeException {
    public PlaceNotFoundException() {
        super("Place not found");
    }
}
