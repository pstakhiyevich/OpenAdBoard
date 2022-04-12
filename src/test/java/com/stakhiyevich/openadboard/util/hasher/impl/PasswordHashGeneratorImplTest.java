package com.stakhiyevich.openadboard.util.hasher.impl;

import com.stakhiyevich.openadboard.util.hasher.PasswordHashGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PasswordHashGeneratorImplTest {

    private PasswordHashGenerator passwordHashGenerator;

    private static final String PASSWORD_ONE = "123456";
    private static final String PASSWORD_TWO = "1234567";

    @BeforeEach
    void setUp() {
        passwordHashGenerator = PasswordHashGeneratorImpl.getInstance();
    }

    @Test
    void generateHashForSamePasswordInput() {
        String firstHash = passwordHashGenerator.generatePasswordHash(PASSWORD_ONE).get();
        String secondHash = passwordHashGenerator.generatePasswordHash(PASSWORD_ONE).get();
        Assertions.assertEquals(firstHash, secondHash);
    }

    @Test
    void generateHashForDifferentPasswordInput() {
        String firstHash = passwordHashGenerator.generatePasswordHash(PASSWORD_ONE).get();
        String secondHash = passwordHashGenerator.generatePasswordHash(PASSWORD_TWO).get();
        Assertions.assertNotEquals(firstHash, secondHash);
    }

    @Test
    void checkPasswordHashForSamePasswordInput() {
        String generatedHash = passwordHashGenerator.generatePasswordHash(PASSWORD_ONE).get();
        Assertions.assertTrue(passwordHashGenerator.checkPasswordHash(PASSWORD_ONE, generatedHash));
    }

    @Test
    void checkPasswordHashForDifferentPasswordInput() {
        String generatedHash = passwordHashGenerator.generatePasswordHash(PASSWORD_ONE).get();
        Assertions.assertFalse(passwordHashGenerator.checkPasswordHash(PASSWORD_TWO, generatedHash));
    }
}