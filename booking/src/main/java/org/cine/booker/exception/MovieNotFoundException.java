package org.cine.booker.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when the movie data loading fails.
 * </p>
 */
public class MovieNotFoundException extends DefaultException {

    public MovieNotFoundException(final String message) {
        super(message);
    }
}
