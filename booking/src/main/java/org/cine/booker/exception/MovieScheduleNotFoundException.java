package org.cine.booker.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when the show schedule data loading fails.
 * </p>
 */
public class MovieScheduleNotFoundException extends DefaultException {

    public MovieScheduleNotFoundException(final String message) {
        super(message);
    }
}
