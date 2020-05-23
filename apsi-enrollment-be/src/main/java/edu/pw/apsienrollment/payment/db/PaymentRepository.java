package edu.pw.apsienrollment.payment.db;

import edu.pw.apsienrollment.payment.db.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from PAYMENT p where p.enrollment.user.id = :userId")
    Page<Payment> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    Optional<Payment> findByUuid(String uuid);
}
