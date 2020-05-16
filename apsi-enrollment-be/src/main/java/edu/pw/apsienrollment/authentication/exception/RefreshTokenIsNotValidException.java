package edu.pw.apsienrollment.authentication.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class RefreshTokenIsNotValidException extends ApsiException {
    public RefreshTokenIsNotValidException() {
        super(ExceptionCode.REFRESH_TOKEN_NOT_VALID, "Refresh token expired or invalidated. Authenticate again", null);
    }
}
