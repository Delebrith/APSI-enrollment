package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class OrderCreateResponseDto {
    String redirectUri;
    String orderId;
    String extOrderId;
    StatusDto status;
}