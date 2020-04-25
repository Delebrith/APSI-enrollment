package edu.pw.apsienrollment.event.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserUnauthorizedToCreateEventException extends RuntimeException {
    public UserUnauthorizedToCreateEventException() {
        super("User does not have rights to create events of given type");
    }
}
