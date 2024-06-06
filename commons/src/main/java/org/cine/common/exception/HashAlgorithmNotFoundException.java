package org.cine.common.exception;

import org.cine.exception.customexception.DefaultException;

/**
 * <p>
 * Handles the exception of algorithm not found while hashing the user password.
 * </p>
 */
public final class HashAlgorithmNotFoundException extends DefaultException {

    public HashAlgorithmNotFoundException(final String message) {
        super(message);
    }
}
