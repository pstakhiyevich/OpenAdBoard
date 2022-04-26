package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CATEGORY_TITLE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.TITLE;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_TITLE_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.CATEGORY_TITLE_PATTERN;

public class AddCategoryFormValidator implements FormValidator {

    private static FormValidator instance;

    private AddCategoryFormValidator() {
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new AddCategoryFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();
        if (!data.get(CATEGORY_TITLE)[0].trim().matches(CATEGORY_TITLE_PATTERN)) {
            validationResult.put(TITLE, MESSAGE_TITLE_WRONG);
        }
        return validationResult;
    }
}
