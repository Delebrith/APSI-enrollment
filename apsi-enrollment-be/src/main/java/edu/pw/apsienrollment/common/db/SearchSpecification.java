package edu.pw.apsienrollment.common.db;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public abstract class SearchSpecification<S> implements Specification<S> {
    protected final Collection<SearchCriteria> searchCriteria;

    protected Predicate toCommonSearchPredicate(SearchCriteria criteria, Root<S> root, CriteriaBuilder criteriaBuilder) {
        Path<String> path = resolvePath(criteria.getKey(), root);
        if (criteria.getOperation().equals(">")) {
            return criteriaBuilder.greaterThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equals("<")) {
            return criteriaBuilder.lessThan(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equals(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equals("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    path, criteria.getValue().toString());
        } else if (criteria.getOperation().equals("=")) {
            if (path.getJavaType() == String.class) {
                return criteriaBuilder.like(
                        criteriaBuilder.function("LOWER", String.class, path),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
            } else {
                return criteriaBuilder.equal(path, criteria.getValue());
            }
        }
        return null;
    }

    protected <T> Path<T> resolvePath(@NonNull String path, @NonNull Root<S> root) {
        String[] paths = path.split("\\.");
        Path<T> res = root.get(paths[0]);
        for(String subPath: Arrays.copyOfRange(paths, 1, paths.length)) {
            res = res.get(subPath);
        }
        return res;
    }
}
