package edu.pw.apsienrollment.payment.payu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class PayUConfig {
    private final String oAuthKey;
    private final String oAuthSecret;
    private final String apiUri;
    private final String notificationUri;
    private final String continueUri;
    private final String posId;

    public PayUConfig(
        @Value("${apsi.payment.payu.OAuthID}") String oAuthKey,
        @Value("${apsi.payment.payu.OAuthSecret}") String oAuthSecret,
        @Value("${apsi.payment.payu.apiUri}") String apiUri,
        @Value("${apsi.payment.payu.notificationUri}") String notificationUri,
        @Value("${apsi.payment.payu.continueUri}") String continueUri,
        @Value("${apsi.payment.payu.posId}") String posId) {
        this.oAuthKey = oAuthKey;
        this.oAuthSecret = oAuthSecret;
        this.apiUri = apiUri;
        this.notificationUri = notificationUri;
        this.continueUri = continueUri;
        this.posId = posId;
    }
}