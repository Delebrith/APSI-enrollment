package edu.pw.apsienrollment.common.api;

import edu.pw.apsienrollment.authentication.exception.RefreshTokenIsNotValidException;
import edu.pw.apsienrollment.authentication.exception.RefreshTokenNotFoundException;
import edu.pw.apsienrollment.common.api.dto.ErrorDto;
import edu.pw.apsienrollment.authentication.exception.InvalidCredentialsException;
import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;
import edu.pw.apsienrollment.enrollment.exception.AlreadySignedUpException;
import edu.pw.apsienrollment.enrollment.exception.AttendeesLimitException;
import edu.pw.apsienrollment.event.exception.EventNotFoundException;
import edu.pw.apsienrollment.event.exception.UserUnauthorizedToCreateEventException;
import edu.pw.apsienrollment.event.meeting.exception.PlaceNotMeetingRequirementsException;
import edu.pw.apsienrollment.event.meeting.exception.SpeakerUnavailableException;
import edu.pw.apsienrollment.user.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class,
            RefreshTokenNotFoundException.class, EventNotFoundException.class})
    @ResponseBody
    ErrorDto handleResourceNotFound(final HttpServletRequest req, final ApsiException ex) {
        return ErrorDto.of(ex, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({UserUnauthorizedToCreateEventException.class})
    @ResponseBody
    ErrorDto handleForbidden(final HttpServletRequest req, final ApsiException ex) {
        return ErrorDto.of(ex, req.getRequestURI());

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({PlaceNotMeetingRequirementsException.class,
            SpeakerUnavailableException.class,
            AlreadySignedUpException.class,
            AttendeesLimitException.class})
    @ResponseBody
    ErrorDto handleConflict(final HttpServletRequest req, final ApsiException ex) {
        return ErrorDto.of(ex, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidCredentialsException.class, RefreshTokenIsNotValidException.class})
    @ResponseBody
    ErrorDto handleBadRequest(final HttpServletRequest req, final ApsiException ex) {
        return ErrorDto.of(ex, req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    ErrorDto handleBadRequest(final HttpServletRequest req, final MethodArgumentNotValidException ex) {
        Map<String, Object> params = parseBindingResult(ex.getBindingResult());
        return ErrorDto.builder()
                .code(ExceptionCode.VALIDATION_FAILED)
                .message(String.format("There were %d validation errors", ex.getBindingResult().getErrorCount()))
                .params(params)
                .uri(req.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    @ResponseBody
    ErrorDto handleBadRequest(final HttpServletRequest req, final BindException ex) {
        Map<String, Object> params = parseBindingResult(ex.getBindingResult());
        return ErrorDto.builder()
                .code(ExceptionCode.VALIDATION_FAILED)
                .message(String.format("There were %d validation errors", ex.getErrorCount()))
                .params(params)
                .uri(req.getRequestURI())
                .build();
    }

    private Map<String, Object> parseBindingResult(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));
    }
}
