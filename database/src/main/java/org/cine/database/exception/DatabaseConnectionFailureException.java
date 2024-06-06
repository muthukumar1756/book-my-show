package org.cine.database.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when unable to get database connection.
 * </p>
 */
public final class DatabaseConnectionFailureException extends DefaultException {

    public DatabaseConnectionFailureException(final String message) {
        super(message);
    }
}

