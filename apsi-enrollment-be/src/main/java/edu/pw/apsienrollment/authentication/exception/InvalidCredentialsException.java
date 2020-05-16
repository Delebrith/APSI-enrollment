package edu.pw.apsienrollment.authentication.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class InvalidCredentialsException extends ApsiException {
    public InvalidCredentialsException() {
        super(ExceptionCode.INVALID_CREDENTIALS, "Invalid username or password", null);
    }
}
