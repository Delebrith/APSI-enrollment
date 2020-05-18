package edu.pw.apsienrollment.enrollment.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class AlreadySignedUpException extends ApsiException {
    public AlreadySignedUpException() {
        super(ExceptionCode.USER_ALREADY_SIGNED, "Already signed up for event", null);
    }
}
