package edu.pw.apsienrollment.enrollment.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class AttendeesLimitException extends ApsiException {
    public AttendeesLimitException() {
        super(ExceptionCode.ATTENDEES_LIMIT_EXCEDED, "No free places left", null);
    }
}
