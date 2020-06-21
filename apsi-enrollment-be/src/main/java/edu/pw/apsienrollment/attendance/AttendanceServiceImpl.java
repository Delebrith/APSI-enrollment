package edu.pw.apsienrollment.attendance;

import com.google.common.collect.ImmutableMap;
import edu.pw.apsienrollment.attendance.db.Attendance;
import edu.pw.apsienrollment.attendance.db.AttendanceRepository;
import edu.pw.apsienrollment.attendance.db.AttendanceStatus;
import edu.pw.apsienrollment.attendance.exception.AttendanceNotFoundException;
import edu.pw.apsienrollment.attendance.exception.UserUnauthorizedToCheckAttendanceException;
import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.event.meeting.MeetingService;
import edu.pw.apsienrollment.qrcode.QRCodeService;
import edu.pw.apsienrollment.user.db.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final QRCodeService qrcodeService;
    private final AuthenticationService authenticationService;
    private final MeetingService meetingService;

    private static final String confirmURLScheme = "attendance/%d/mark-as-present?token=%s";
    private static final Integer qrCodeImageSize = 640;

    @Override
    public Attendance putIntoAttendanceList(Meeting meeting, User user) {
        Attendance attendance = Attendance.builder()
                .meeting(meeting)
                .user(user)
                .attendanceStatus(AttendanceStatus.UNCHECKED)
                .build();
        return attendanceRepository.save(attendance);
    }

    @Override
    public byte[] getQrCode(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(AttendanceNotFoundException::new);
        return qrcodeService.generateQRCode(
                String.format(confirmURLScheme, attendance.getId(), attendance.getToken()),
                qrCodeImageSize,
                qrCodeImageSize);
    }

    @Override
    public void markAsPresent(Long id, String token, Long meetingId) {
        Attendance attendance = attendanceRepository.findByIdAndTokenAndMeeting_Id(id, token, meetingId)
                .orElseThrow(() -> new AttendanceNotFoundException(
                        "Attendance not found", ImmutableMap.of("id", id, "token", token, "meetingId", meetingId)));
        checkIfUserIsOrganizerOrSpeaker(attendance.getMeeting());
        attendance.setAttendanceStatus(AttendanceStatus.PRESENT);
        attendanceRepository.save(attendance);
    }

    private void checkIfUserIsOrganizerOrSpeaker(Meeting meeting) {
        User authenticated = authenticationService.getAuthenticatedUser();
        if (!meeting.getSpeakers().contains(authenticated)
                && !meeting.getEvent().getOrganizer().equals(authenticated)) {
            throw new UserUnauthorizedToCheckAttendanceException();
        }
    }
        
    public Page<Attendance> getMeetingsOfAuthorizedUser(Integer page, Integer pageSize) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        return attendanceRepository.findByUser(authenticatedUser, PageRequest.of(page, pageSize));
    }

    @Override
    public List<Attendance> getAttendanceList(Meeting meeting) {
        checkIfUserIsOrganizerOrSpeaker(meeting);
        return attendanceRepository.findByMeetingOrderByUser_Surname(meeting);
    }

}
