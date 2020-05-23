package edu.pw.apsienrollment.payment.payu;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import edu.pw.apsienrollment.payment.PaymentConfig;
import edu.pw.apsienrollment.payment.PaymentProviderService;
import edu.pw.apsienrollment.payment.db.Payment;
import edu.pw.apsienrollment.payment.payu.exception.PayUException;
import edu.pw.apsienrollment.payment.payu.api.dto.OAuthResponseDto;
import edu.pw.apsienrollment.payment.payu.api.dto.OrderCreateRequestDto;
import edu.pw.apsienrollment.payment.payu.api.dto.OrderCreateResponseDto;

@Service
public class PaymentProviderServicePayUImpl implements PaymentProviderService {
    private final String OAUTH_REQUEST_BODY = "grant_type=client_credentials&client_id=%s&client_secret=%s";
    private final String OAUTH_ENDPOINT = "/pl/standard/user/oauth/authorize";
    private final String ORDERS_ENDPOINT = "/api/v2_1/orders";

    private final RestTemplate payURestTemplate;
    private final PayUConfig payUConfig;
    private final PaymentConfig paymentConfig;

    public PaymentProviderServicePayUImpl(
        PayUConfig payUConfig,
        PaymentConfig paymentConfig,
        RestTemplateBuilder restTemplateBuilder) {
        this.payUConfig = payUConfig;
        this.paymentConfig = paymentConfig;
        this.payURestTemplate = restTemplateBuilder.rootUri(payUConfig.getApiUri()).build();
    }

    @Override
    public Payment createProviderPayment(Payment payment, Enrollment enrollment, String customerIp) {
        OAuthResponseDto authorization = authorizePayU();
        OrderCreateResponseDto response = createPayUOrder(enrollment, payment.getUuid(), customerIp, authorization);

        payment.setProviderOrderId(response.getExtOrderId());
        payment.setProviderRedirectUrl(response.getRedirectUri());

        return payment;
    }

    private OAuthResponseDto authorizePayU() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<String>(
            String.format(OAUTH_REQUEST_BODY,
            payUConfig.getOAuthKey(), payUConfig.getOAuthSecret()),
            headers);

        System.out.println(entity.getBody());
        System.out.println(payURestTemplate.toString());
        
        ResponseEntity<OAuthResponseDto> response = payURestTemplate.exchange(
            OAUTH_ENDPOINT, HttpMethod.POST, entity, OAuthResponseDto.class);
        
        if (!response.getStatusCode().is2xxSuccessful())
            throw new PayUException();
        
        return response.getBody();
    }

    private OrderCreateResponseDto createPayUOrder(
        Enrollment enrollment, String extOrderId, String customerIp, OAuthResponseDto authorization) {
        OrderCreateRequestDto requestDto = OrderCreateRequestDto.create(
            enrollment, extOrderId, customerIp,
            payUConfig.getPosId(),
            paymentConfig.getCurrency(),
            payUConfig.getContinueUri(),
            payUConfig.getNotificationUri());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(authorization.getAccess_token());
        HttpEntity<OrderCreateRequestDto> requestEntity = new HttpEntity<OrderCreateRequestDto>(requestDto, requestHeaders);
        ResponseEntity<OrderCreateResponseDto> response = payURestTemplate.exchange(
            ORDERS_ENDPOINT, HttpMethod.POST, requestEntity, OrderCreateResponseDto.class);
        
        if (!response.getStatusCode().is2xxSuccessful() && !response.getStatusCode().is3xxRedirection()) 
            throw new PayUException();
        
        return response.getBody();
    }
}
