package edu.pw.apsienrollment.event.meeting.exception;

import com.google.common.collect.ImmutableMap;
import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaceNotMeetingRequirementsException extends ApsiException {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public PlaceNotMeetingRequirementsException(String name, int capacity, int requiredCapacity) {
        super(ExceptionCode.PLACE_TOO_SMALL,
                String.format("Place %s too small. Capacity: %d, required: %d", name, capacity, requiredCapacity),
                ImmutableMap.of(
                        "name", name,
                        "capacity", capacity,
                        "requiredCapacity", requiredCapacity
                ));
    }

    public PlaceNotMeetingRequirementsException(LocalDateTime availableFrom, LocalDateTime availableTo) {
        super(ExceptionCode.PLACE_NOT_AVAILABLE,
                String.format("Place not available from %s to %s",
                DATE_TIME_FORMATTER.format(availableFrom),
                DATE_TIME_FORMATTER.format(availableTo)),
                ImmutableMap.of(
                        "from", availableFrom,
                        "to", availableTo
                ));
    }
}
