package com.stakhiyevich.openadboard.util.encoder;

import java.util.Optional;

/**
 * The LinkEncoder interface translates special characters from the URL to a representation that adheres to the spec and can be correctly understood and interpreted.
 */
public interface LinkEncoder {
    /**
     * Encodes a specified string.
     *
     * @param value a string to encode
     * @return an optional object of an encoded string
     */
    Optional<String> encodeURL(String value);

    /**
     * Decodes a specified string.
     *
     * @param value a string to decode
     * @return an optional object of a decoded string
     */
    Optional<String> decodeURL(String value);
}
