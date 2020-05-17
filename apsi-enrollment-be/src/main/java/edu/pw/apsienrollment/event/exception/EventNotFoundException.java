package edu.pw.apsienrollment.event.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class EventNotFoundException extends ApsiException {
    public EventNotFoundException() {
        super(ExceptionCode.EVENT_NOT_FOUND, "Event not found", null);
    }
}