package edu.pw.apsienrollment.payment.payu.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class PayUException extends ApsiException {
    public PayUException() {
        super(ExceptionCode.PAYU_EXCEPTION, "Error creating PayU order", null);
    }
}