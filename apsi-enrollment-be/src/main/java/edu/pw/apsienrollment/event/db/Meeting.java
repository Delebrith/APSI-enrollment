package edu.pw.apsienrollment.event.db;

import edu.pw.apsienrollment.place.db.Place;
import edu.pw.apsienrollment.user.db.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "MEETING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    @ManyToMany
    @JoinTable(name = "MEETING_SPEAKERS",
            joinColumns = @JoinColumn(name = "MEETING_ID", nullable = false, referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SPEAKER_ID", nullable = false, referencedColumnName = "ID"))
    @Builder.Default
    private Set<User> speakers = new HashSet<>();

    @ManyToOne
    @JoinColumn(nullable = false, name = "PLACE_ID")
    private Place place;

    @ManyToOne
    @JoinColumn(nullable = false, name = "EVENT_ID")
    private Event event;
}
