package com.stakhiyevich.openadboard.util.validator.impl;

import com.stakhiyevich.openadboard.util.validator.FormValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.*;

class AddItemFormValidatorTest {

    public static final String[] correctTitle = {"Simple Title"};
    public static final String[] incorrectTitle = {"Simple Title!"};

    public static final String[] correctPrice = {"123"};
    public static final String[] incorrectPrice = {"123!"};

    public static final String[] correctDescription = {"Simple description"};
    public static final String[] incorrectDescription = {"Simple description#&@&#"};

    public static final String[] correctContact = {"@telegram123"};
    public static final String[] incorrectContact = {"don't call me!"};

    public static final String[] correctCity = {"City"};
    public static final String[] incorrectCity = {"City!"};

    public static final String[] correctCategory = {"Category"};
    public static final String[] incorrectCategory = {"Category!!!"};


    Map<String, String[]> correctInputData;
    Map<String, String[]> incorrectInputData;
    FormValidator validator;

    @BeforeEach
    void setUp() {
        validator = AddItemFormValidator.getInstance();

        correctInputData = new HashMap<>();
        correctInputData.put(TITLE, correctTitle);
        correctInputData.put(PRICE, correctPrice);
        correctInputData.put(DESCRIPTION, correctDescription);
        correctInputData.put(CONTACT, correctContact);
        correctInputData.put(CITY, correctCity);
        correctInputData.put(CATEGORY, correctCategory);

        incorrectInputData = new HashMap<>();
        incorrectInputData.put(TITLE, incorrectTitle);
        incorrectInputData.put(PRICE, incorrectPrice);
        incorrectInputData.put(DESCRIPTION, incorrectDescription);
        incorrectInputData.put(CONTACT, incorrectContact);
        incorrectInputData.put(CITY, incorrectCity);
        incorrectInputData.put(CATEGORY, incorrectCategory);
    }

    @Test
    void validateForm() {
        Map<String, String> result = validator.validateForm(correctInputData);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void validateFormFalse() {
        Map<String, String> expected = new HashMap<>();
        expected.put(TITLE, MESSAGE_TITLE_WRONG);
        expected.put(PRICE, MESSAGE_PRICE_WRONG);
        expected.put(DESCRIPTION, MESSAGE_DESCRIPTION_WRONG);
        expected.put(CONTACT, MESSAGE_CONTACT_WRONG);
        expected.put(CITY, MESSAGE_CITY_WRONG);
        expected.put(CATEGORY, MESSAGE_CATEGORY_WRONG);
        Map<String, String> result = validator.validateForm(incorrectInputData);
        Assertions.assertEquals(expected, result);
    }
}