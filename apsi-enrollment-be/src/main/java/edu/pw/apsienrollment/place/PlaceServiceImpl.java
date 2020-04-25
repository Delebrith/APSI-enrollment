package edu.pw.apsienrollment.place;

import edu.pw.apsienrollment.common.db.SearchQueryParser;
import edu.pw.apsienrollment.place.db.Place;
import edu.pw.apsienrollment.place.db.PlaceRepository;
import edu.pw.apsienrollment.place.db.PlaceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    @Override
    public Place getPlace(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public Optional<Place> getPlaceIfAvailable(
            Long id, LocalDateTime availableFrom, LocalDateTime availableTo) {
        return placeRepository.findByIdAndAvailableBetween(id, availableFrom, availableTo);
    }

    @Override
    public Page<Place> findPlaces(String query, Integer page, Integer size) {
        return placeRepository
                .findAll(new PlaceSpecification(SearchQueryParser.parse(query)), PageRequest.of(page, size));
    }

    @Override
    public Page<Place> findPlaces(Integer page, Integer size) {
        return placeRepository.findAll(PageRequest.of(page, size));
    }
}
