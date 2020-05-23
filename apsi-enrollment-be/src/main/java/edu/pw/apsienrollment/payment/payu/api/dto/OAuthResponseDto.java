package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.Value;

@Value
public class OAuthResponseDto {
    String access_token;
    String token_type;
    Long expires_in;
    String grant_type;
}