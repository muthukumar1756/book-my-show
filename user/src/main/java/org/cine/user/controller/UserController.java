package org.cine.user.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import org.cine.user.model.UserLoginDetails;
import org.cine.user.model.UserProfileUpdateDetails;
import org.cine.user.service.UserService;
import org.cine.user.service.internal.impl.UserServiceImpl;
import org.cine.user.model.User;

/**
 * <p>
 * Handles the user related operation and responsible for processing user input through rest api
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Path("/user")
public final class UserController {

    private final UserService userService;

    private UserController() {
        userService = UserServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final UserController USER_CONTROLLER = new UserController();
    }

    /**
     * <p>
     * Gets the object of the user controller class.
     * </p>
     *
     * @return The user controller object
     */
    public static UserController getInstance() {
        return InstanceHolder.USER_CONTROLLER;
    }

    /**
     * <p>
     * Creates the new user.
     * </p>
     *
     * @param user Represents the {@link User}
     * @return byte array of json response
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] createUserProfile(final User user) {
        return userService.createUserProfile(user).asBytes();
    }

    /**
     * <p>
     * Gets the user if the phone_number and password matches.
     * </p>
     *
     * @param userLoginDetails Represents the instance of user login dto
     * @return byte array of json response
     */
    @Path("/login")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] userLogin(final UserLoginDetails userLoginDetails) {
        return userService.loginUser(userLoginDetails).asBytes();
    }

    /**
     * <p>
     * Gets the user if the id matches.
     * </p>
     *
     * @param userId Represents the password of the current user
     * @return byte array of json response
     */
    @Path("/{userId}")
    @GET
    @Produces("application/json")
    public byte[] getUserById(@PathParam("userId") final long userId) {
        return userService.getUserById(userId).asBytes();
    }

    /**
     * <p>
     * Updates the data of the current user.
     * </p>
     *
     * @param userProfileUpdateDetails Represents the instance of user profile update dto
     * @return byte array of json response
     */
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] updateUserData(final UserProfileUpdateDetails userProfileUpdateDetails) {
        return userService.updateUserProfile(userProfileUpdateDetails).asBytes();
    }
}