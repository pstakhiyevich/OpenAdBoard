package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.EMAIL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.NAME;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EMAIL_WRONG;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_NAME_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.EMAIL_PATTERN;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.NAME_PATTERN;

public class EditUserFormValidator implements FormValidator {

    private static FormValidator instance;

    private EditUserFormValidator() {
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new EditUserFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> data) {
        Map<String, String> validationResult = new HashMap<>();

        if (!data.get(NAME)[0].trim().matches(NAME_PATTERN)) {
            validationResult.put(NAME, MESSAGE_NAME_WRONG);
        }
        if (!data.get(EMAIL)[0].trim().matches(EMAIL_PATTERN)) {
            validationResult.put(EMAIL, MESSAGE_EMAIL_WRONG);
        }
        return validationResult;
    }
}
