package com.stakhiyevich.openadboard.controller.command;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The ClientCommand interface
 */
public interface Command {
    /**
     * Executes a given command.
     *
     * @param request the HttpServletRequest object
     * @return a resulted Router object that contains the result page and routing type
     */
    Router execute(HttpServletRequest request);

    /**
     * Parses a long input parameter.
     *
     * @param input an input request parameter
     * @return parsed long parameter or 0 otherwise
     */
    default long parseLongParameter(String input) {
        try {
            return Long.parseLong((input));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Parses an int input parameter.
     *
     * @param input an input request parameter
     * @return parsed int parameter or 0 otherwise
     */
    default int parseIntParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
