package edu.pw.apsienrollment.payment.db;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "PAYMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String providerOrderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Column
    @NotNull
    private String providerRedirectUrl;

    @Column
    @Default
    @NotNull
    private String uuid = UUID.randomUUID().toString();
}
