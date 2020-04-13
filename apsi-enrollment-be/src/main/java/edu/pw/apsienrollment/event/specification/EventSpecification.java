package edu.pw.apsienrollment.event.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import lombok.RequiredArgsConstructor;

import edu.pw.apsienrollment.event.db.Event;



@AllArgsConstructor(staticName = "of")
public class EventSpecification implements Specification<Event> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(
                    root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase(":")) {
            if(root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
