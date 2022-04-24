package com.stakhiyevich.openadboard.util.hasher;

import java.util.Optional;


/**
 * A Password hash generator interface that generates and checks a password's hash.
 */
public interface PasswordHashGenerator {
    /**
     * Generates a hash from a specified user password.
     *
     * @param password a user's password
     * @return an optional object of a generated password hash
     */
    Optional<String> generatePasswordHash(String password);

    /**
     * Checks whether a password matches a hash.
     *
     * @param password a user's password
     * @param key a user password's hash
     * @return whether a password matches a hash
     */
    boolean checkPasswordHash(String password, String key);
}
