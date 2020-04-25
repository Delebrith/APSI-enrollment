package edu.pw.apsienrollment.place;

import edu.pw.apsienrollment.place.db.Place;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PlaceService {
    Place getPlace(Long id);

    Optional<Place> getPlaceIfAvailable(Long id, LocalDateTime availableFrom, LocalDateTime availableTo);

    Page<Place> findPlaces(String query, Integer page, Integer size);

    Page<Place> findPlaces(Integer page, Integer size);
}
