package org.cine.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.cine.common.hibernate.validatorgroup.user.UpdateUserValidator;

/**
 * Handles the data for the user profile update.
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public record UserProfileUpdateDetails(

        @Positive(message = "User id can't be negative", groups = UpdateUserValidator.class)
        Long id,
        @NotNull(message = "Update data type can't be null", groups = UpdateUserValidator.class)
        UserProfileField updateDataType,
        @Pattern(message = "Enter a valid name", regexp = "^[A-Za-z][A-Za-z\\s]{3,20}$", groups = UpdateUserValidator.class)
        String name,
        @Pattern(message = "Enter a valid phone number", regexp = "^(0/91)?[6789]\\d{9}$", groups = UpdateUserValidator.class)
        String phoneNumber,
        @Pattern(message = "Enter a valid password", regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$", groups = UpdateUserValidator.class)
        String password,
        @Pattern(message = "Enter a valid email id", regexp = "^[a-z][a-z\\d._]+@[a-z]{5,20}.[a-z]{2,3}$", groups = UpdateUserValidator.class)
        String emailId) {
}
