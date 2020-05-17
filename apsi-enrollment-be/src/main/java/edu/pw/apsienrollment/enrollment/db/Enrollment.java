package edu.pw.apsienrollment.enrollment.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import edu.pw.apsienrollment.enrollment.db.EnrollmentStatus;
import edu.pw.apsienrollment.user.db.User;
import edu.pw.apsienrollment.event.db.Event;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "Enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

}
