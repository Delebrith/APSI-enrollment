package edu.pw.apsienrollment.attendance.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import edu.pw.apsienrollment.user.db.User;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByIdAndToken(Long id, String token);

    Page<Attendance> findByUser(User user, Pageable pageable);
}
