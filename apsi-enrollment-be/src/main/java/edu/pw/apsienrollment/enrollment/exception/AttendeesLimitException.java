package edu.pw.apsienrollment.enrollment.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class AttendeesLimitException extends ApsiException {
    public AttendeesLimitException() {
        super(ExceptionCode.NO_FREE_PLACES, "No free places left", null);
    }
}
