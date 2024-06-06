package org.cine.user.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when unable to load user data.
 * </p>
 */
public class UserProfileCreationException extends DefaultException {

    public UserProfileCreationException(final String message) {
        super(message);
    }
}