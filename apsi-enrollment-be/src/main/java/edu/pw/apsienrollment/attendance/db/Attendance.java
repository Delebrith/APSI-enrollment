package edu.pw.apsienrollment.attendance.db;

import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;

@Entity(name = "ATTENDANCE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus;

    @Column(nullable = false)
    @Builder.Default
    private String token = RandomStringUtils.randomAlphanumeric(32);
}
