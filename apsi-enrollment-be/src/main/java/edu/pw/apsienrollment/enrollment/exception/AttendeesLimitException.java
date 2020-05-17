package edu.pw.apsienrollment.enrollment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttendeesLimitException extends RuntimeException {
    public AttendeesLimitException() {
        super("No free places for event left");
    }
}
