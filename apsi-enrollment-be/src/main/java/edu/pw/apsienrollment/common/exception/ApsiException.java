package edu.pw.apsienrollment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ApsiException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final String message;
    private final Map<String, Object> params;
}
