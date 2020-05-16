package edu.pw.apsienrollment.place;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PlaceNotFoundException extends ApsiException {
    public PlaceNotFoundException() {
        super(ExceptionCode.PLACE_NOT_FOUND, "Place not found", null);
    }
}
