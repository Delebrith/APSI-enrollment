package edu.pw.apsienrollment.attendance.db;

import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByIdAndTokenAndMeeting_Id(Long id, String token, Long meetingId);

    Page<Attendance> findByUser(User user, Pageable pageable);

    List<Attendance> findByMeetingOrderByUser_Surname(@Param("meeting") Meeting meeting);

    boolean existsByMeeting_IdAndAttendanceStatus(Long meetingId, AttendanceStatus present);

    List<Attendance> findByMeeting_IdAndAndAttendanceStatus(Long meetingId, AttendanceStatus unchecked);
}
