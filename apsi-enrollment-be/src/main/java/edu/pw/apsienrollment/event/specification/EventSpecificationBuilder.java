package edu.pw.apsienrollment.event.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import lombok.NoArgsConstructor;

import edu.pw.apsienrollment.event.db.Event;


@NoArgsConstructor
public class EventSpecificationBuilder {

    private final List<SearchCriteria> params = new ArrayList<>();

    public EventSpecificationBuilder with(String key, String op, Object value) {
        params.add(new SearchCriteria(key, op, value));
        return this;
    }

    public Specification<Event> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Event>> specs = params.stream()
                .map(EventSpecification::of)
                .collect(Collectors.toList());
        Specification<Event> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}

