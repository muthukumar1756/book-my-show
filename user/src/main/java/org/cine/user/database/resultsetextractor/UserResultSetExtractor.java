package org.cine.user.database.resultsetextractor;

import java.util.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cine.user.exception.UserProfileNotFoundException;
import org.cine.user.model.User;

/**
 * <p>
 *  Methods to extract the result set data of user.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class UserResultSetExtractor {

    private UserResultSetExtractor() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final UserResultSetExtractor USER_RESULT_SET_EXTRACTOR = new UserResultSetExtractor();
    }

    /**
     * <p>
     * Gets the instance of the user result set handler class.
     * </p>
     *
     * @return The user result set handler instance
     */
    public static UserResultSetExtractor getInstance() {
        return InstanceHolder.USER_RESULT_SET_EXTRACTOR;
    }

    /**
     * <p>
     * Gets the user profile from the result set.
     * </p>
     *
     * @param resultSet Represents the result set data from the executed query.
     * @return The user object
     */
    public Optional<User> getUser(final ResultSet resultSet) {
        try {

            if (resultSet.next()) {
                final User user = new User.UserBuilder().setId(resultSet.getLong(1))
                        .setName(resultSet.getString(2)).setPhoneNumber(resultSet.getString(3))
                        .setEmailId(resultSet.getString(4)).setPassword(resultSet.getString(5))
                        .build();

                return Optional.of(user);
            }

            return Optional.empty();
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }
}
