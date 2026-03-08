package org.cine.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController() {
        userService = UserServiceImpl.getInstance();
    }

    /**
     * <p>
     * Creates the new user.
     * </p>
     *
     * @param user Represents the {@link User}
     * @return JSON response
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public byte[] createUserProfile(@RequestBody final User user) {
        return userService.createUserProfile(user).asBytes();
    }

    /**
     * <p>
     * Gets the user if the phone_number and password matches.
     * </p>
     *
     * @param userLoginDetails Represents the instance of user login dto
     * @return JSON response
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public byte[] userLogin(@RequestBody final UserLoginDetails userLoginDetails) {
        return userService.loginUser(userLoginDetails).asBytes();
    }

    /**
     * <p>
     * Gets the user if the id matches.
     * </p>
     *
     * @param userId Represents the id of the user
     * @return JSON response
     */
    @GetMapping(value = "/{userId}", produces = "application/json")
    public byte[] getUserById(@PathVariable("userId") final long userId) {
        return userService.getUserById(userId).asBytes();
    }

    /**
     * <p>
     * Updates the data of the current user.
     * </p>
     *
     * @param userProfileUpdateDetails Represents the instance of user profile update dto
     * @return JSON response
     */
    @PutMapping(consumes = "application/json", produces = "application/json")
    public byte[] updateUserData(@RequestBody final UserProfileUpdateDetails userProfileUpdateDetails) {
        return userService.updateUserProfile(userProfileUpdateDetails).asBytes();
    }
}