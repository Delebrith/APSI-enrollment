package edu.pw.apsienrollment.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    @Query("select u from USER_ u where u.id = :id and not(exists" +
            "   (select m from MEETING m where " +
            "       u in elements(m.speakers) and" +
            "       ((m.start >= :availableFrom and m.start < :availableTo) or" +
            "       (m.end > :availableFrom and m.end <= :availableTo) or" +
            "       (m.start <= :availableFrom and m.end >= :availableTo))" +
            "   ))")
    Optional<User> findByIdAndAvailability(@Param("id") Integer id,
                                           @Param("availableFrom") LocalDateTime availableFrom,
                                           @Param("availableTo") LocalDateTime availableTo);
}
