package edu.pw.apsienrollment.common.db;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchQueryParser {
    public static final String KEY = "([\\w.]+?)";
    public static final String OPERATOR = "(=|>|>=|<|<=)";
    public static final String VALUE = "([\\w:\\-;\\p{Space}]+?)";

    public static Collection<SearchCriteria> parse(@NonNull String searchQuery) {
        Pattern pattern = Pattern.compile(KEY + OPERATOR + VALUE + ",", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchQuery + ",");
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        while(matcher.find()) {
            searchCriteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return searchCriteriaList;
    }
 }
