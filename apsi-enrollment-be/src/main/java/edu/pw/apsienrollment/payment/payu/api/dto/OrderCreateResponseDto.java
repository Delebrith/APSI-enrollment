package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.Value;

@Value
public class OrderCreateResponseDto {
    String redirectUri;
    String orderId;
    String extOrderId;
    StatusDto status;
}