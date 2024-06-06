package org.cine.user.service;

import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonObject;
import org.cine.user.model.User;
import org.cine.user.model.UserLoginDetails;
import org.cine.user.model.UserProfileUpdateDetails;

/**
 * <p>
 * Provides the services for the user.
 * </p>
 *
 * @author Muthu kumar v
 * @version 1.0
 */
public interface UserService {

    /**
     * <p>
     * Creates the new user profile.
     * </p>
     *
     * @param user Represents the {@link User}
     * @return The response of user profile creation
     */
    JsonObject createUserProfile(final User user);

    /**
     * <p>
     * Gets the user profile if the phone_number and password matches.
     * </p>
     *
     * @param userLoginDetails Represents the instance of user login dto
     * @return The user object
     */
    JsonObject loginUser(final UserLoginDetails userLoginDetails);

    /**
     * <p>
     * Gets the user profile if the id matches.
     * </p>
     *
     * @param userId Represents the password of the user
     * @return The user object
     */
    JsonObject getUserById(final long userId);

    /**
     * <p>
     * Updates the data of the user.
     * </p>
     *
     * @param userProfileUpdateDetails Represents the instance of user profile update dto
     * @return Response for the user profile updation
     */
    JsonObject updateUserProfile(final UserProfileUpdateDetails userProfileUpdateDetails);
}
