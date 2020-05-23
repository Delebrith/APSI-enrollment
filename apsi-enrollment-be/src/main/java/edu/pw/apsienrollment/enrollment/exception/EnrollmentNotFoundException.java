package edu.pw.apsienrollment.enrollment.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class EnrollmentNotFoundException extends ApsiException {
    public EnrollmentNotFoundException() {
        super(ExceptionCode.ENROLLMENT_NOT_FOUND, "Enrollment not found", null);
    }
}
