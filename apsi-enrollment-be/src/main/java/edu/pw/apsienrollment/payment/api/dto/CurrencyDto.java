package edu.pw.apsienrollment.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CurrencyDto {
    private String currency;
}