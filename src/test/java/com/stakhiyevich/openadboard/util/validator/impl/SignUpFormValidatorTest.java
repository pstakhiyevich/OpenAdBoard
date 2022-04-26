package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.*;

class SignUpFormValidatorTest {

    public static final String[] correctName = {"John"};
    public static final String[] incorrectName = {"John!"};

    public static final String[] correctEmail = {"john@gmail.com"};
    public static final String[] incorrectEmail = {"john@gmailcom"};

    public static final String[] correctPassword = {"32394AJRFJjfe"};
    public static final String[] incorrectPassword = {"123"};

    Map<String, String[]> correctInputData;
    Map<String, String[]> incorrectInputData;
    FormValidator validator;

    @BeforeEach
    void setUp() {
        validator = SignUpFormValidator.getInstance();
        correctInputData = new HashMap<>();
        correctInputData.put(NAME, correctName);
        correctInputData.put(EMAIL, correctEmail);
        correctInputData.put(PASSWORD, correctPassword);
        correctInputData.put(REPEATED_PASSWORD, correctPassword);

        incorrectInputData = new HashMap<>();
        incorrectInputData.put(NAME, incorrectName);
        incorrectInputData.put(EMAIL, incorrectEmail);
        incorrectInputData.put(PASSWORD, incorrectPassword);
        incorrectInputData.put(REPEATED_PASSWORD, incorrectPassword);
    }

    @Test
    void validateForm() {
        Map<String, String> result = validator.validateForm(correctInputData);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void validateFormFalse() {
        Map<String, String> expected = new HashMap<>();
        expected.put(NAME, MESSAGE_NAME_WRONG);
        expected.put(EMAIL, MESSAGE_EMAIL_WRONG);
        expected.put(PASSWORD, MESSAGE_PASSWORD_WRONG);
        expected.put(REPEATED_PASSWORD, MESSAGE_REPEATED_PASSWORD_WRONG);
        Map<String, String> result = validator.validateForm(incorrectInputData);
        Assertions.assertEquals(expected, result);
    }
}