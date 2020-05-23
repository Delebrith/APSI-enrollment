package edu.pw.apsienrollment.payment.payu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class PayUConfig {
    private final @Getter String oAuthKey;
    private final @Getter String oAuthSecret;
    private final @Getter String apiUri;
    private final @Getter String notificationUri;
    private final @Getter String continueUri;
    private final @Getter String posId;

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