package edu.pw.apsienrollment.payment.payu.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pw.apsienrollment.payment.PaymentService;
import edu.pw.apsienrollment.payment.payu.api.dto.NotificationDto;
import edu.pw.apsienrollment.payment.payu.api.dto.OrderStatus;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("payment/payu")
@RequiredArgsConstructor
@Validated
public class PayUController {
    private final PaymentService paymentService;

    @PostMapping("notify")
    ResponseEntity<Void> notify(@RequestBody NotificationDto notificationDto) {
        if (notificationDto.getOrder().getStatus().equals(OrderStatus.COMPLETED)) {
            paymentService.completePayment(notificationDto.getOrder().getExtOrderId());
        }
        else if (notificationDto.getOrder().getStatus().equals(OrderStatus.CANCELED))
            paymentService.cancelPayment(notificationDto.getOrder().getExtOrderId());
            
        return ResponseEntity.ok().build();
    }
}