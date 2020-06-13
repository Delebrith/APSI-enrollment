package edu.pw.apsienrollment.event.db;

import edu.pw.apsienrollment.common.db.SearchCriteria;
import edu.pw.apsienrollment.common.db.SearchSpecification;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.meeting.Meeting;
import edu.pw.apsienrollment.user.db.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class EventSpecification extends SearchSpecification<Event> {

    public EventSpecification(Collection<SearchCriteria> searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Collection<Predicate> predicates = new ArrayList<>();

        this.searchCriteria.stream()
                .map(criteria -> {
                    if (criteria.getKey().equalsIgnoreCase("eventType")) {
                        return criteriaBuilder.equal(root.get("eventType"), EventType.valueOf(criteria.getValue().toString()));
                    } else {
                        return this.toCommonSearchPredicate(criteria, root, criteriaBuilder);
                    }
                })
                .filter(Objects::nonNull)
                .forEach(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Specification<Event> toSpecificationWithSpeaker(User speaker) {
        return (event, query, builder) -> {
            query.distinct(true);

            Root<Meeting> meetings = query.from(Meeting.class);

            return builder.and(
                builder.isMember(speaker, meetings.get("speakers")),
                builder.equal(event, meetings.get("event")),
                this.toPredicate(event, query, builder));
        };
    }
}
