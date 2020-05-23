package edu.pw.apsienrollment.payment.payu.api.dto;

import java.util.Collections;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderCreateRequestDto {
    String extOrderId;
    String customerIp;
    String merchantPosId;
    String description;
    String currencyCode;
    String totalAmount;
    String continueUrl;
    String notifyUrl;
    Iterable<ProductDto> products;

    public static OrderCreateRequestDto create(
            Enrollment enrollment,
            String extOrderId,
            String customerIp,
            String posId,
            String currency,
            String continueUrl,
            String notifyUrl) {
        ProductDto product  = ProductDto.of(enrollment);
        return builder()
            .extOrderId(extOrderId)
            .customerIp(customerIp)
            .merchantPosId(posId)
            .description(
                String.format("Enrollment of %s to %s.",
                enrollment.getUser().getName(),
                enrollment.getEvent().getName()))
            .currencyCode(currency)
            .totalAmount(product.getUnitPrice())
            .continueUrl(continueUrl)
            .notifyUrl(notifyUrl)
            .products(Collections.singletonList(product))
            .build();
    }
}