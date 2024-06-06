package org.cine.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.cine.common.hibernate.validatorgroup.user.LoginUserValidator;

/**
 * Handles the data for the user profile login.
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public record UserLoginDetails(

        @NotNull(message = "Login type can't be null", groups = LoginUserValidator.class)
        UserProfileField loginType,
        @Pattern(message = "Enter a valid phone number", regexp = "^(0/91)?[6789]\\d{9}$", groups = LoginUserValidator.class)
        String phoneNumber,
        @Pattern(message = "Enter a valid email id", regexp = "^[a-z][a-z\\d._]+@[a-z]{5,20}.[a-z]{2,3}$", groups = LoginUserValidator.class)
        String emailId,
        @NotNull(message = "Password can't be null", groups = {LoginUserValidator.class})
        @Pattern(message = "Enter a valid password", regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$", groups = LoginUserValidator.class)
        String password) {
}
