package edu.pw.apsienrollment.authentication.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class RefreshTokenNotFoundException extends ApsiException {
    public RefreshTokenNotFoundException() {
        super(ExceptionCode.REFRESH_TOKEN_NOT_FOUND, "Refresh token not found", null);
    }
}
