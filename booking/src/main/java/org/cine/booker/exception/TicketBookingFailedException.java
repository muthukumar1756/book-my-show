package org.cine.booker.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception when the ticket booking is failed.
 * </p>
 */
public class TicketBookingFailedException extends DefaultException {

    public TicketBookingFailedException(final String message) {
        super(message);
    }
}
