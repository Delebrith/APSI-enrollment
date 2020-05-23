package edu.pw.apsienrollment.payment;

import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.enrollment.EnrollmentService;
import edu.pw.apsienrollment.enrollment.db.Enrollment;
import edu.pw.apsienrollment.payment.db.Payment;
import edu.pw.apsienrollment.payment.db.PaymentRepository;
import edu.pw.apsienrollment.payment.db.PaymentStatus;
import edu.pw.apsienrollment.payment.exception.PaymentNotFoundException;
import edu.pw.apsienrollment.user.db.User;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class PaymentServiceImpl implements PaymentService {
    private final AuthenticationService authenticationService;
    private final PaymentRepository paymentRepository;
    private final PaymentConfig paymentConfig;
    private final EnrollmentService enrollmentService;
    private final PaymentProviderService paymentProviderService;

    @Override
    public Page<Payment> findUserPayments(Integer page, Integer pageSize) {
        User user = authenticationService.getAuthenticatedUser();
        return paymentRepository.findByUserId(user.getId(), PageRequest.of(page, pageSize));
    }
    
    @Override
    public void completePayment(String uuid) {
        Payment payment = paymentRepository.findByUuid(uuid).orElseThrow(PaymentNotFoundException::new);
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        
        enrollmentService.enroll(payment.getEnrollment());
    }

    @Override
    public void cancelPayment(String uuid) {
        Payment payment = paymentRepository.findByUuid(uuid).orElseThrow(PaymentNotFoundException::new);
        payment.setStatus(PaymentStatus.CANCELED);
        paymentRepository.save(payment);
    }

    @Override
    public String getCurrency() {
        return paymentConfig.getCurrency();
    }

    @Override
    public Payment createPayment(Long enrollmentId, String customerIp) {
        Enrollment enrollment = enrollmentService.getById(enrollmentId);
        Payment payment = Payment.builder()
            .status(PaymentStatus.NEW)
            .enrollment(enrollment)
            .build();
        
        payment = paymentProviderService.createProviderPayment(payment, enrollment, customerIp);
        return paymentRepository.save(payment);
    }
}
