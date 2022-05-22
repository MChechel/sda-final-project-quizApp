package com.teamA.utils;

import org.springframework.data.domain.Sort;

public class QuizUtils {
    public static final String DEFAULT_ITEMS_PER_PAGE ="10";

    public static Sort getSortOfColumn(String sort, String order) {
        if (order.toLowerCase().equals("asc")) {
            return Sort.by(sort).ascending();
        } else {
            return Sort.by(sort).descending();
        }
    }
}
