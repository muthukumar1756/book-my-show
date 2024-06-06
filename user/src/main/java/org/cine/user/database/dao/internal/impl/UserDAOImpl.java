package org.cine.user.database.dao.internal.impl;

import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cine.common.hashgenerator.PasswordHashGenerator;
import org.cine.database.connection.DataBaseConnection;
import org.cine.user.database.resultsetextractor.UserResultSetExtractor;
import org.cine.user.database.dao.UserDAO;
import org.cine.user.exception.UserProfileCreationException;
import org.cine.user.exception.UserProfileNotFoundException;
import org.cine.user.exception.UserProfileUpdateException;
import org.cine.user.model.User;

/**
 * <p>
 * Implements the data base service of the user related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class UserDAOImpl implements UserDAO {

    private final UserResultSetExtractor userResultSetExtractor;
    private final Connection connection;

    private UserDAOImpl() {
        userResultSetExtractor = UserResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final UserDAO USER_DAO = new UserDAOImpl();
    }

    /**
     * <p>
     * Gets the object of the user database implementation class.
     * </p>
     *
     * @return The user database service implementation object
     */
    public static UserDAO getInstance() {
        return InstanceHolder.USER_DAO;
    }

    /**
     * {@inheritDoc}
     *
     * @param user Represents the {@link User}
     * @return True if user is created, false otherwise
     */
    public boolean createUserProfile(final User user) {
        final String query = """
                insert into users (name, phone_number, email_id, password)
                values (?, ?, ?, ?)""";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getEmailId());
            preparedStatement.setString(4, PasswordHashGenerator.getInstance().hashPassword(user.getPassword()));

            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new UserProfileCreationException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return True if user is exist, false otherwise
     */
    public boolean isUserExist(final String phoneNumber, final String emailId) {
        final String query = "select count(*) from users where phone_number = ? or email_id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.setString(2, emailId);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return 0 < resultSet.getInt(1);
            }

            return false;
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userDataType Represents the data type of the user
     * @param userData Represents the data of the user
     * @param password Represents the password of the user
     * @return The user object
     */
    public Optional<User> getUser(final String userDataType, final String userData, final String password) {
        final String query = String.join("",
                "select id, name, phone_number, email_id, password from users where ",
                userDataType, " = ? and password = ?");

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userData);
            preparedStatement.setString(2, password);

            return userResultSetExtractor.getUser(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents the id of the user
     * @return The user object
     */
    @Override
    public Optional<User> getUserById(final long userId) {
        final String query = " select name, phone_number, email_id, password from users where id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);

            return userResultSetExtractor.getUser(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new UserProfileNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents the id of {@link User}
     * @param type Represents the type of data to be updated
     * @param userData Represents the value of data to be updated
     * @return True if user data is updated, false otherwise
     */
    @Override
    public boolean updateUserProfile(final long userId, final String type, final String userData) {
        final String query = String.join("", "update users set ", type, " = ? where id = ?");

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userData);
            preparedStatement.setLong(2, userId);

            return 0 < preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new UserProfileUpdateException(message.getMessage());
        }
    }
}