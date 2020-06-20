package edu.pw.apsienrollment.attendance.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class UserUnauthorizedToCheckAttendanceException extends ApsiException {
    public UserUnauthorizedToCheckAttendanceException() {
        super(ExceptionCode.USER_NOT_AUTHORIZED_TO_CHECK_ATTENDANCE,
                "User is not event organizer or meeting speaker", null);
    }
}
