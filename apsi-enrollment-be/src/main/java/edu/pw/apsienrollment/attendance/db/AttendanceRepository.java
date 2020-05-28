package edu.pw.apsienrollment.attendance.db;

import org.springframework.data.jpa.repository.JpaRepository;

interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
