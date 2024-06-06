package org.cine.common.hashgenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.cine.common.exception.HashAlgorithmNotFoundException;

/**
 * <p>
 * Provides hashed password for security purposes.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class PasswordHashGenerator {

    private static final Logger LOGGER = LogManager.getLogger(PasswordHashGenerator.class);

    private PasswordHashGenerator() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final PasswordHashGenerator PASSWORD_HASH_GENERATOR = new PasswordHashGenerator();
    }

    /**
     * <p>
     * Gets the instance of the password generator class.
     * </p>
     *
     * @return The password hash generator instance
     */
    public static PasswordHashGenerator getInstance() {
        return InstanceHolder.PASSWORD_HASH_GENERATOR;
    }

    /**
     * <p>
     * Hashes and returns the password.
     * </p>
     *
     * @param password Represents the password of the user
     * @return The hashed password
     */
    public String hashPassword(final String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] encodedHash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hashString = new StringBuilder();

            for (final byte hashByte : encodedHash) {
                hashString.append(String.format("%02x", hashByte));
            }

            return hashString.substring(0, 25);
        } catch (NoSuchAlgorithmException message) {
            LOGGER.error(message.getMessage());
            throw new HashAlgorithmNotFoundException(message.getMessage());
        }
    }
}