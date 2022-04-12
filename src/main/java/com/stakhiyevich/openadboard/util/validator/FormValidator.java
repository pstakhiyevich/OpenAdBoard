package com.stakhiyevich.openadboard.util.validator;

import java.util.Map;


public interface FormValidator {
    Map<String, String> validateForm(Map<String, String[]> data);
}
