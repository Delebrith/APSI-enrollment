package edu.pw.apsienrollment.payment.payu.api.dto;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import lombok.Builder;
import lombok.Value;
import lombok.Builder.Default;

@Value
@Builder
public class ProductDto {
    String name;
    @Default
    String quantity = "1";
    String unitPrice;

    static ProductDto of(Enrollment enrollment) {
        return builder()
            .name(enrollment.getEvent().getName())
            .unitPrice(Integer.toString((int)(enrollment.getEvent().getCost() * 100)))
            .build();
    }
}