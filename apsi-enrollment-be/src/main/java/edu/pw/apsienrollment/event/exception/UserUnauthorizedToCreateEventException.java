package edu.pw.apsienrollment.event.exception;

import com.google.common.collect.ImmutableMap;
import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;
import edu.pw.apsienrollment.event.db.EventType;

public class UserUnauthorizedToCreateEventException extends ApsiException {
    public UserUnauthorizedToCreateEventException(EventType eventType) {
        super(ExceptionCode.UNAUTHORIZED_TO_CREATE_EVENT,
                "User does not have rights to create events of given type",
                ImmutableMap.of("eventType", eventType));
    }
}
