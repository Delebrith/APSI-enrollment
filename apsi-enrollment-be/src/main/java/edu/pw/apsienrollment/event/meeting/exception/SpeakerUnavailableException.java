package edu.pw.apsienrollment.event.meeting.exception;

import com.google.common.collect.ImmutableMap;
import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpeakerUnavailableException extends ApsiException {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public SpeakerUnavailableException(LocalDateTime availableFrom, LocalDateTime availableTo) {
        super(ExceptionCode.SPEAKER_UNAVAILABLE, String.format("Speaker not available from %s to %s",
                DATE_TIME_FORMATTER.format(availableFrom),
                DATE_TIME_FORMATTER.format(availableTo)),
                ImmutableMap.of(
                        "from", availableFrom,
                        "to", availableTo
                ));
    }
}
