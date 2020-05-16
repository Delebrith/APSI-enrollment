package edu.pw.apsienrollment.common.api.dto;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private ExceptionCode code;
    private String message;
    private Map<String, Object> params;
    private String uri;

    public static ErrorDto of(ApsiException exception, String uri) {
        return ErrorDto.builder()
                .code(exception.getExceptionCode())
                .message(exception.getMessage())
                .params(exception.getParams())
                .uri(uri)
                .build();
    }
}
