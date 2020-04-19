package edu.pw.apsienrollment.event.specification;

import java.util.Arrays;

import javax.persistence.criteria.*;

import edu.pw.apsienrollment.event.db.EventType;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import edu.pw.apsienrollment.event.db.Event;


@AllArgsConstructor(staticName = "of")
public class EventSpecification implements Specification<Event> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Path<String> path = resolvePath(criteria.getKey(), root);
        if (criteria.getKey().equalsIgnoreCase("eventType")) {
            return criteriaBuilder.equal(
                    path, EventType.valueOf(criteria.getValue().toString()));
        }
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                return criteriaBuilder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(path, criteria.getValue());
            }
        }
        return null;
    }

    static <T> Path<T> resolvePath(@NonNull String path, @NonNull Root<Event> root) {
        String[] paths = path.split("\\.");
        System.out.println("Resolving path for: " + path);
        System.out.println("Path for root: " + paths[0]);
        Path<T> res = root.get(paths[0]);
        for(String subPath: Arrays.copyOfRange(paths, 1, paths.length)) {
            System.out.println("Subpath for: " + subPath);
            res = res.get(subPath);
        }
        return res;
    }
}
