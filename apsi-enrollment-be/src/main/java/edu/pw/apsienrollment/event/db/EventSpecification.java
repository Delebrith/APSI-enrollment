package edu.pw.apsienrollment.event.db;

import edu.pw.apsienrollment.common.db.SearchCriteria;
import edu.pw.apsienrollment.common.db.SearchSpecification;
import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.db.EventType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
}
