package edu.pw.apsienrollment.attendance.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByIdAndToken(Long id, String token);
}
