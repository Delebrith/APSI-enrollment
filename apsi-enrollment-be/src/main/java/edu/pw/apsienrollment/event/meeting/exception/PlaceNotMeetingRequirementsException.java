package edu.pw.apsienrollment.event.meeting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlaceNotMeetingRequirementsException extends RuntimeException {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public PlaceNotMeetingRequirementsException(String name, int capacity, int requiredCapacity) {
        super(String.format("Place %s too small. Capacity: %d, required: %d", name, capacity, requiredCapacity));
    }

    public PlaceNotMeetingRequirementsException(LocalDateTime availableFrom, LocalDateTime availableTo) {
        super(String.format("Place not available from %s to %s",
                DATE_TIME_FORMATTER.format(availableFrom),
                DATE_TIME_FORMATTER.format(availableTo)));
    }
}
