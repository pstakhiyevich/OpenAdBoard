package com.stakhiyevich.openadboard.util.validator;

import java.util.Map;

/**
 * A form validator interface.
 */
public interface FormValidator {
    /**
     * Validates a form with a specified form data.
     *
     * @param data a form data from the http request
     * @return a map with validation feedback for each row that could be empty if the form is valid
     */
    Map<String, String> validateForm(Map<String, String[]> data);
}
