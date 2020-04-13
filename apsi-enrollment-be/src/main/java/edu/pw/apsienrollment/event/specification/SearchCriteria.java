package edu.pw.apsienrollment.event.specification;

import lombok.Value;

@Value
public class SearchCriteria {
    String key;
    String operation;
    Object value;

    public boolean isOrPredicate() {
        return false;
    }

    public boolean isAndPredicate() {
        return !isOrPredicate();
    }
}

