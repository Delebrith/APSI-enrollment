package edu.pw.apsienrollment.attendance.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

import java.util.Map;

public class AttendanceNotFoundException extends ApsiException {
    public AttendanceNotFoundException() {
        super(ExceptionCode.ATTENDANCE_NOT_FOUND,
                "Attendance not found. User was not enrolled?", null);
    }

    public AttendanceNotFoundException(String message, Map<String, Object> params) {
        super(ExceptionCode.ATTENDANCE_NOT_FOUND, message, params);
    }
}
