package org.cine.exception.customexception;

/**
 * <p>
 * Handles the exception when the user action fails.
 * </p>
 */
public class DefaultException extends RuntimeException {

    public DefaultException(final String message) {
        super(message);
    }
}
