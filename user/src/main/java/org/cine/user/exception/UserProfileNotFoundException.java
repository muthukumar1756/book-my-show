package org.cine.user.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when the user data is not found.
 * </p>
 */
public class UserProfileNotFoundException extends DefaultException {

    public UserProfileNotFoundException(final String message) {
        super(message);
    }
}