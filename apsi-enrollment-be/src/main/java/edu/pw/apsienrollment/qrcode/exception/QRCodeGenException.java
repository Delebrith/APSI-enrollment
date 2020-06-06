package edu.pw.apsienrollment.qrcode.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class QRCodeGenException extends ApsiException {
    public QRCodeGenException() {
        super(ExceptionCode.QR_CODE_GEN_FAILED, "Error during QR code generation", null);
    }
}
