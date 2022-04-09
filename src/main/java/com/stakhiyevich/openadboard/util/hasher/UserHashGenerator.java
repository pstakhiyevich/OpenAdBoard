package com.stakhiyevich.openadboard.util.hasher;

import java.util.Optional;

public interface UserHashGenerator {
    Optional<String> generateUserHash(String email);
}
