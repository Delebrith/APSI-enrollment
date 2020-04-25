package edu.pw.apsienrollment.common.db;

import lombok.Value;

@Value
public class SearchCriteria {
    String key;
    String operation;
    Object value;
}

