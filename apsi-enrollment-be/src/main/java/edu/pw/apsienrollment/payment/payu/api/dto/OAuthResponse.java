package edu.pw.apsienrollment.payment.payu.api.dto;

import lombok.Value;

@Value
public class OAuthResponse {
    String access_token;
    String token_type;
    Long expires_in;
    String grant_type;
}