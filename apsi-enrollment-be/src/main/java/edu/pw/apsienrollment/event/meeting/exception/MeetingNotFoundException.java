package edu.pw.apsienrollment.event.meeting.exception;

import edu.pw.apsienrollment.common.exception.ApsiException;
import edu.pw.apsienrollment.common.exception.ExceptionCode;

public class MeetingNotFoundException extends ApsiException {
    public MeetingNotFoundException() {
        super(ExceptionCode.MEETING_NOT_FOUND, "Meeting not found", null);
    }
}
