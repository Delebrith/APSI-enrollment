package edu.pw.apsienrollment.user;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class UserNotFoundException extends ApsiException {
    UserNotFoundException() {
        super(ExceptionCode.USER_NOT_FOUND, "User not found", null);
    }
}
