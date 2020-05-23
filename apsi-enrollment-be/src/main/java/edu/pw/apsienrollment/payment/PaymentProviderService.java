package edu.pw.apsienrollment.payment;

import edu.pw.apsienrollment.enrollment.db.Enrollment;
import edu.pw.apsienrollment.payment.db.Payment;

public interface PaymentProviderService {
    Payment createProviderPayment(Payment payment, Enrollment enrollment, String customerIp);
}