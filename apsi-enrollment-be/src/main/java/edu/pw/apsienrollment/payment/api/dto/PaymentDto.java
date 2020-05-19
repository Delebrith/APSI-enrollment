package edu.pw.apsienrollment.payment.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.enrollment.api.dto.EnrollmentDto;
import edu.pw.apsienrollment.payment.db.Payment;
import edu.pw.apsienrollment.payment.db.PaymentStatus;


@Value
@Builder
public class PaymentDto {
    Long id;
    String orderId;
    PaymentStatus status;
    EnrollmentDto enrollment;

    public static PaymentDto of(@NonNull Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .enrollment(EnrollmentDto.of(payment.getEnrollment()))
                .build();
    }
}
