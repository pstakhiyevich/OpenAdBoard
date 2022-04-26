package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.COMMENT_TEXT;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_COMMENT_TEXT_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.COMMENT_TEXT_PATTERN;

public class CommentFormValidator implements FormValidator {

    private static FormValidator instance;

    private CommentFormValidator() {
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new CommentFormValidator();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateForm(Map<String, String[]> validationData) {
        Map<String, String> validationResult = new HashMap<>();
        //1-500, letters, digits, whitespaces, :!?.,_'- symbols
        if (!validationData.get(COMMENT_TEXT)[0].trim().matches(COMMENT_TEXT_PATTERN)) {
            validationResult.put(COMMENT_TEXT, MESSAGE_COMMENT_TEXT_WRONG);
        }
        return validationResult;
    }
}
