package edu.pw.apsienrollment.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class PaymentConfig {
    private final @Getter String currency;

    PaymentConfig(@Value("${apsi.payment.currency}") String currency) {
        this.currency = currency;
    }
}