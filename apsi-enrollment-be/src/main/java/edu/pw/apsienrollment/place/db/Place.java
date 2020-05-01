package edu.pw.apsienrollment.place.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "PLACE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private Integer capacity;
}
