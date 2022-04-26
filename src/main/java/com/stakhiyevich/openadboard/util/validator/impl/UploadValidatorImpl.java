package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.UploadValidator;
import jakarta.servlet.http.Part;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.PICTURE;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_PICTURE_WRONG;
import static com.stakhiyevich.openadboard.util.validator.ValidatorPatternHolder.PICTURE_EXTENSION_PATTERN;

public class UploadValidatorImpl implements UploadValidator {

    private static UploadValidator instance;

    private UploadValidatorImpl() {
    }

    public static UploadValidator getInstance() {
        if (instance == null) {
            instance = new UploadValidatorImpl();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateUpload(List<Part> parts) {
        Map<String, String> validationResult = new HashMap<>();
        if (parts.isEmpty() || !parts.get(0).getSubmittedFileName().matches(PICTURE_EXTENSION_PATTERN) ||
                parts.stream().anyMatch(part -> (part.getSize() / (1024 * 1024)) >= 10)) {
            validationResult.put(PICTURE, MESSAGE_PICTURE_WRONG);
        }
        return validationResult;
    }
}
