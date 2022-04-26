package com.stakhiyevich.openadboard.util.validator;

import jakarta.servlet.http.Part;

import java.util.List;
import java.util.Map;

/**
 * An upload validator interface.
 */
public interface UploadValidator {
    /**
     * Validates uploaded data parts
     *
     * @param parts a list of parts that was received within a multipart/from-data POST request
     * @return whether uploaded data parts are valid
     */
    Map<String, String> validateUpload(List<Part> parts);
}
