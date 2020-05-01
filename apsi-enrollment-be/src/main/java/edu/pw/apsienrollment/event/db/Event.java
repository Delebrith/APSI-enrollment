package edu.pw.apsienrollment.event.db;

import edu.pw.apsienrollment.user.db.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "Event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private Integer attendeesLimit;

    @Formula("(select min(m.start) from meeting as m where m.event_id = id)")
    LocalDateTime start;
    @Formula("(select max(m.end) from meeting as m where m.event_id = id)")
    LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "ORGANIZER_ID", nullable = false)
    private User organizer;

    @Builder.Default
    private Float cost = 0f;
}
