package edu.pw.apsienrollment.enrollment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadySignedUpException extends RuntimeException {
    public AlreadySignedUpException() {
        super("User has already signed up for event");
    }
}
