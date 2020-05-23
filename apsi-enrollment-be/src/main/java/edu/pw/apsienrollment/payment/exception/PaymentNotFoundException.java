package edu.pw.apsienrollment.payment.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class PaymentNotFoundException extends ApsiException {
    public PaymentNotFoundException() {
        super(ExceptionCode.PAYMENT_NOT_FOUND, "Payment not found", null);
    }
}