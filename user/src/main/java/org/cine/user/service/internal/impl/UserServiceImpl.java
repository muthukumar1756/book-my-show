package org.cine.user.service.internal.impl;

import java.util.Optional;

import org.cine.common.hibernate.HibernateEntityValidator;
import org.cine.user.model.UserLoginDetails;
import org.cine.user.model.UserProfileUpdateDetails;
import org.cine.user.service.UserService;
import org.cine.user.database.dao.UserDAO;
import org.cine.user.database.dao.internal.impl.UserDAOImpl;
import org.cine.user.model.User;
import org.cine.user.model.UserProfileField;
import org.cine.common.json.JsonObject;
import org.cine.common.hashgenerator.PasswordHashGenerator;
import org.cine.common.hibernate.impl.HibernateEntityValidatorImpl;
import org.cine.common.hibernate.validatorgroup.user.GetUserValidator;
import org.cine.common.hibernate.validatorgroup.user.LoginUserValidator;
import org.cine.common.hibernate.validatorgroup.user.CreateUserValidator;
import org.cine.common.hibernate.validatorgroup.user.UpdateUserValidator;

/**
 * <p>
 * Implements the service of the user related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class UserServiceImpl implements UserService {

    private static final String STATUS = "status";
    private final HibernateEntityValidator validatorFactory;
    private final UserDAO userDAO;

    private UserServiceImpl() {
        userDAO = UserDAOImpl.getInstance();
        validatorFactory = HibernateEntityValidatorImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final UserService USER_SERVICE = new UserServiceImpl();
    }

    /**
     * <p>
     * Gets the instance of the user service implementation class.
     * </p>
     *
     * @return The user service implementation class object
     */
    public static UserService getInstance() {
        return InstanceHolder.USER_SERVICE;
    }

    /**
     * {@inheritDoc}
     *
     * @param user Represents the {@link User}
     * @return The response of user profile creation
     */
    @Override
    public JsonObject createUserProfile(final User user) {
        final JsonObject jsonObject = validatorFactory.validate(user, CreateUserValidator.class);

        if (jsonObject.isEmpty()) {

            if (userDAO.isUserExist(user.getPhoneNumber(), user.getEmailId())) {
                return jsonObject.put(STATUS, "User profile creation failed");
            }

            return userDAO.createUserProfile(user) ?
                    jsonObject.put(STATUS, "User profile was created") :
                    jsonObject.put(STATUS, "User profile creation failed");
        }

        return jsonObject;
    }

    /**
     * {@inheritDoc}
     *
     * @param userLoginDetails Represents the instance of user login dto
     * @return The user object
     */
    @Override
    public JsonObject loginUser(final UserLoginDetails userLoginDetails) {
        final JsonObject jsonObject = validatorFactory.validate(userLoginDetails, LoginUserValidator.class);

        if (jsonObject.isEmpty()) {
            final String hashPassword = PasswordHashGenerator.getInstance().hashPassword(userLoginDetails.password());

            final Optional<User> user = switch (userLoginDetails.loginType()) {
                case PHONE_NUMBER -> userDAO.getUser(UserProfileField.PHONE_NUMBER.name(),
                        userLoginDetails.phoneNumber(), hashPassword);
                case EMAIL_ID -> userDAO.getUser(UserProfileField.EMAIL_ID.name(),
                        userLoginDetails.emailId(), hashPassword);
                default -> Optional.empty();
            };

            return user.isPresent() ?
                    jsonObject.put(STATUS, String.join(" ", "User login successful welcome",
                            user.get().getName())) :
                    jsonObject.put(STATUS, "User login failed");
        }

        return jsonObject;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId Represents the password of the user
     * @return The user object
     */
    @Override
    public JsonObject getUserById(final long userId) {
        final User userPojo = new User.UserBuilder().setId(userId).build();
        final JsonObject jsonObject = validatorFactory.validate(userPojo, GetUserValidator.class);

        if (jsonObject.isEmpty()) {
            final Optional<User> user = userDAO.getUserById(userId);

            return user.isPresent() ? jsonObject.build(user.get()) :
                    jsonObject.put(STATUS, "User not found");
        }

        return jsonObject;
    }

    /**
     * {@inheritDoc}
     *
     * @param userProfileUpdateDetails Represents the instance of user profile update dto
     * @return Response for the user profile updation
     */
    @Override
    public JsonObject updateUserProfile(final UserProfileUpdateDetails userProfileUpdateDetails) {
        final JsonObject jsonObject = validatorFactory.validate(userProfileUpdateDetails, UpdateUserValidator.class);

        if (jsonObject.isEmpty()) {
            final boolean updateStatus = switch (userProfileUpdateDetails.updateDataType()) {
                case NAME -> userDAO.updateUserProfile(userProfileUpdateDetails.id(),
                        userProfileUpdateDetails.updateDataType().name(), userProfileUpdateDetails.name());
                case EMAIL_ID -> userDAO.updateUserProfile(userProfileUpdateDetails.id(),
                        userProfileUpdateDetails.updateDataType().name(), userProfileUpdateDetails.emailId());
                case PHONE_NUMBER -> userDAO.updateUserProfile(userProfileUpdateDetails.id(),
                        userProfileUpdateDetails.updateDataType().name(), userProfileUpdateDetails.phoneNumber());
                case PASSWORD -> userDAO.updateUserProfile(userProfileUpdateDetails.id(),
                        userProfileUpdateDetails.updateDataType().name(), userProfileUpdateDetails.password());
            };

            return updateStatus ? jsonObject.put(STATUS, "User profile is updated") :
                    jsonObject.put(STATUS, "User profile updation failed");
        }

        return jsonObject;
    }
}