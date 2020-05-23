package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class OrderDto {
    String orderId;
    String extOrderId;
    //PayU sends also some data that will not be used on our side
    String status;
}