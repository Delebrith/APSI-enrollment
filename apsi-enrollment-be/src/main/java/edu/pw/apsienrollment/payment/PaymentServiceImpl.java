package edu.pw.apsienrollment.payment;

import edu.pw.apsienrollment.authentication.AuthenticationService;
import edu.pw.apsienrollment.payment.db.Payment;
import edu.pw.apsienrollment.payment.db.PaymentRepository;
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

    @Override
    public Page<Payment> findUserPayments(Integer page, Integer pageSize) {
        User user = authenticationService.getAuthenticatedUser();
        return paymentRepository.findByUserId(user.getId(), PageRequest.of(page, pageSize));
    }
}
