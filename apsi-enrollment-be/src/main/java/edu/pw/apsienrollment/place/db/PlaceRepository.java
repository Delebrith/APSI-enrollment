package edu.pw.apsienrollment.place.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {
    @Query("select p from PLACE p where p.id = :id and not(exists" +
            "   (select m from MEETING m where " +
            "       m.place.id = :id and" +
            "       ((m.start >= :availableFrom and m.start <= :availableTo) or" +
            "       (m.end >= :availableFrom and m.end <= :availableTo) or" +
            "       (m.start <= :availableFrom and m.end >= :availableTo))" +
            "   ))")
    Optional<Place> findByIdAndAvailableBetween(@Param("id") Long id,
                                                @Param("availableFrom") LocalDateTime availableFrom,
                                                @Param("availableTo") LocalDateTime availableTo);
}
