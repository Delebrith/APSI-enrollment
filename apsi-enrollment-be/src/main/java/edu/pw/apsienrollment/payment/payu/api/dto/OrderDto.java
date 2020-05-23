package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.Value;

@Value
public class OrderDto {
    String orderId;
    String extOrderId;
    //PayU sends also some data that will not be used on our side
    String status;
}