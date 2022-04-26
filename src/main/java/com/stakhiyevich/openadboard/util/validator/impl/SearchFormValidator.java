package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.SEARCH_QUERY;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_SEARCH_QUERY_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.TITLE_PATTERN;

public class SearchFormValidator implements FormValidator {

    private static FormValidator instance;

    private SearchFormValidator() {
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SearchFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();
        if (!data.get(SEARCH_QUERY)[0].trim().matches(TITLE_PATTERN)) {
            validationResult.put(SEARCH_QUERY, MESSAGE_SEARCH_QUERY_WRONG);
        }
        return validationResult;
    }
}
