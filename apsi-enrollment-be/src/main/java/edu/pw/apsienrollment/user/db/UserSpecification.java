package edu.pw.apsienrollment.user.db;

import edu.pw.apsienrollment.common.db.SearchCriteria;
import edu.pw.apsienrollment.common.db.SearchSpecification;
import edu.pw.apsienrollment.event.db.Meeting;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class UserSpecification extends SearchSpecification<User> {
    public UserSpecification(Collection<SearchCriteria> searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Collection<Predicate> predicates = new ArrayList<>();

        this.searchCriteria.stream()
                .map(criteria -> {
                    if (criteria.getKey().equalsIgnoreCase("availableBetween")) {
                        String[] availability = ((String) criteria.getValue()).split(";");
                        LocalDateTime availableFrom = LocalDateTime.parse(availability[0]);
                        LocalDateTime availableTo = LocalDateTime.parse(availability[1]);
                        return getAvailabilityPredicate(availableFrom, availableTo, root, query, criteriaBuilder);

                    } else if (criteria.getKey().equalsIgnoreCase("roles")) {
                        String[] roles = ((String) criteria.getValue()).split(";");
                        return Stream.of(roles)
                                .map(role -> criteriaBuilder.isMember(UserRole.valueOf(role), root.get("roles")))
                                .reduce(criteriaBuilder::or)
                                .orElse(null);

                    } else {
                        return this.toCommonSearchPredicate(criteria, root, criteriaBuilder);
                    }
                })
                .filter(Objects::nonNull)
                .forEach(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getAvailabilityPredicate(LocalDateTime from, LocalDateTime to,
                                               Root<User> root, CriteriaQuery<?> query,
                                               CriteriaBuilder criteriaBuilder) {

        Subquery<User> meetingSubquery = query.subquery(User.class);
        Root<User> userSubRoot = meetingSubquery.correlate(root);
        Root<Meeting> meetingRoot = meetingSubquery.from(Meeting.class);
        Join<Meeting, User> meetingUserJoin = meetingRoot.join("speakers");

        meetingSubquery.select(meetingUserJoin);
        meetingSubquery.where(criteriaBuilder.and(
                criteriaBuilder.equal(userSubRoot, meetingUserJoin),
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
