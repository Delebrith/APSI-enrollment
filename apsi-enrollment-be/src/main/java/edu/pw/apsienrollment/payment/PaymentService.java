package edu.pw.apsienrollment.payment;

import edu.pw.apsienrollment.payment.db.Payment;

import org.springframework.data.domain.Page;

public interface PaymentService {
    Page<Payment> findUserPayments(Integer page, Integer pageSize);
}
