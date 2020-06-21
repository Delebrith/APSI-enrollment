package edu.pw.apsienrollment.event.meeting;

import edu.pw.apsienrollment.event.api.dto.MeetingRequestDto;
import edu.pw.apsienrollment.event.db.Event;

import java.util.List;

public interface MeetingService {
    Meeting getById(Long id);

    Meeting createMeeting(Event event, MeetingRequestDto dto);

    List<Meeting> getMeetings(Event event);
}
