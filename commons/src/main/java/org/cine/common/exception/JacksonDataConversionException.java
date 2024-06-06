package org.cine.common.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when unable to process the data with jackson json provider.
 * </p>
 */
public final class JacksonDataConversionException extends DefaultException {

    public JacksonDataConversionException(final String message) {
        super(message);
    }
}
