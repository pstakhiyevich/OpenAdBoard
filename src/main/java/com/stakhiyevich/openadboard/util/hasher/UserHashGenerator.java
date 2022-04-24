package com.stakhiyevich.openadboard.util.hasher;

import java.util.Optional;

public interface UserHashGenerator {
    /**
     * Generates a user hash from user's email for account activation functionality
     *
     * @param email a user's email
     * @return an optional object of a generated hash
     */
    Optional<String> generateUserHash(String email);
}
