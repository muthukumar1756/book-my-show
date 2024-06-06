package org.cine.user.model;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.cine.common.hibernate.validatorgroup.user.CreateUserValidator;
import org.cine.common.hibernate.validatorgroup.user.GetUserValidator;

/**
 * <p>
 * Represents user entity with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class User {

    @Positive(message = "User id can't be negative", groups = GetUserValidator.class)
    private Long id;
    @NotNull(message = "Name can't be null", groups = {CreateUserValidator.class})
    @Pattern(message = "Enter a valid name", regexp = "^[A-Za-z][A-Za-z\\s]{3,20}$", groups = CreateUserValidator.class)
    private String name;
    @NotNull(message = "PhoneNumber can't be null", groups = {CreateUserValidator.class})
    @Pattern(message = "Enter a valid phone number", regexp = "^(0/91)?[6789]\\d{9}$", groups = CreateUserValidator.class)
    private String phoneNumber;
    @NotNull(message = "Password can't be null", groups = {CreateUserValidator.class})
    @Pattern(message = "Enter a valid password", regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$", groups = CreateUserValidator.class)
    private String password;
    @Pattern(message = "Enter a valid email id", regexp = "^[a-z][a-z\\d._]+@[a-z]{5,20}.[a-z]{2,3}$", groups = CreateUserValidator.class)
    private String emailId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof User) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class UserBuilder {

        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setId(final Long id) {
            user.id = id;
                 
            return this;
        }

        public UserBuilder setName(final String name) {
            user.name = name;
                 
            return this;
        }

        public UserBuilder setPhoneNumber(final String phoneNumber) {
            user.phoneNumber = phoneNumber;
                 
            return this;
        }

        public UserBuilder setPassword(final String password) {
            user.password = password;
                 
            return this;
        }

        public UserBuilder setEmailId(final String emailId) {
            user.emailId = emailId;
                 
            return this;
        }

        public User build() {
            return user;
        }
    }
}
