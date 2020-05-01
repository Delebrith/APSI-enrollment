package edu.pw.apsienrollment.place.db;

import edu.pw.apsienrollment.common.db.SearchCriteria;
import edu.pw.apsienrollment.common.db.SearchSpecification;
import edu.pw.apsienrollment.event.meeting.Meeting;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PlaceSpecification extends SearchSpecification<Place> {
    public PlaceSpecification(Collection<SearchCriteria> searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Collection<Predicate> predicates = new ArrayList<>();

        this.searchCriteria.stream()
                .map(criteria -> {
                    if (criteria.getKey().equalsIgnoreCase("availableBetween")) {
                        String[] availability = ((String) criteria.getValue()).split(";");
                        LocalDateTime availableFrom = LocalDateTime.parse(availability[0], DateTimeFormatter.ISO_DATE_TIME);
                        LocalDateTime availableTo = LocalDateTime.parse(availability[1], DateTimeFormatter.ISO_DATE_TIME);

                        return getAvailabilityPredicate(availableFrom, availableTo, root, query, criteriaBuilder);
                    } else {
                        return this.toCommonSearchPredicate(criteria, root, criteriaBuilder);
                    }
                })
                .filter(Objects::nonNull)
                .forEach(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getAvailabilityPredicate(LocalDateTime from, LocalDateTime to,
                                               Root<Place> root, CriteriaQuery<?> query,
                                               CriteriaBuilder criteriaBuilder) {

        Subquery<Place> meetingSubquery = query.subquery(Place.class);
        Root<Place> placeRoot = meetingSubquery.correlate(root);
        Root<Meeting> meetingRoot = meetingSubquery.from(Meeting.class);
        Join<Meeting, Place> meetingPlaceJoin = meetingRoot.join("place");

        meetingSubquery.select(meetingPlaceJoin);
        meetingSubquery.where(criteriaBuilder.and(
                criteriaBuilder.equal(placeRoot, meetingPlaceJoin),
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(meetingRoot.get("start"), from),
                                criteriaBuilder.lessThanOrEqualTo(meetingRoot.get("start"), to)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(meetingRoot.get("end"), from),
                                criteriaBuilder.lessThanOrEqualTo(meetingRoot.get("end"), to)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThanOrEqualTo(meetingRoot.get("start"), from),
                                criteriaBuilder.greaterThanOrEqualTo(meetingRoot.get("end"), to)
                        )
                ))
        );

        return criteriaBuilder.not(criteriaBuilder.exists(meetingSubquery));
    }
}
