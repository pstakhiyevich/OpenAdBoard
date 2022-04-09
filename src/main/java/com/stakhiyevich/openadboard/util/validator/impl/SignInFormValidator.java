package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.EMAIL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.PASSWORD;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EMAIL_WRONG;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_PASSWORD_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.EMAIL_PATTERN;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.PASSWORD_PATTERN;

public class SignInFormValidator implements FormValidator {
    private static FormValidator instance;
    private SignInFormValidator() {
    }
    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SignInFormValidator();
        }
        return instance;
    }
    @Override
    public Map<String, String> validateForm(Map<String, String[]> validationData) {
        Map<String, String> validationResult = new HashMap<>();
        if (!validationData.get(EMAIL)[0].matches(EMAIL_PATTERN)) {
            validationResult.put(EMAIL, MESSAGE_EMAIL_WRONG);
        }
        if (!validationData.get(PASSWORD)[0].matches(PASSWORD_PATTERN)) {
            validationResult.put(PASSWORD, MESSAGE_PASSWORD_WRONG);
        }
        return validationResult;
    }
}
