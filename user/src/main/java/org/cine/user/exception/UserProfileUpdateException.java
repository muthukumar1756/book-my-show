package org.cine.user.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when the user data cant be updated.
 * </p>
 */
public class UserProfileUpdateException extends DefaultException {

    public UserProfileUpdateException(final String message) {
        super(message);
    }
}
