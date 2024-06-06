package org.cine.common.hibernate.impl;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.cine.common.hibernate.HibernateEntityValidator;
import org.hibernate.validator.HibernateValidator;

import org.cine.common.json.JsonObject;
import org.cine.common.json.JsonFactory;

/**
 * <p>
 *  Handles the method for the hibernate validation from this class.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class HibernateEntityValidatorImpl implements HibernateEntityValidator {

    private final JsonFactory jsonFactory;
    private static Validator validator;

    private HibernateEntityValidatorImpl() {
        jsonFactory = JsonFactory.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final HibernateEntityValidatorImpl VALIDATOR_FACTORY = new HibernateEntityValidatorImpl();
    }

    /**
     * <p>
     * Gets the validator factory object.
     * </p>
     *
     * @return The object of validator factory class
     */
    public static HibernateEntityValidatorImpl getInstance() {
        return InstanceHolder.VALIDATOR_FACTORY;
    }

    /**
     * <p>
     * Gets the validator object.
     * </p>
     *
     * @return The object of validator
     */
    private static Validator getValidator() {
        if (null == validator) {
            validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory()
                    .getValidator();
        }

        return validator;
    }

    /**
     * <p>
     * Validates the object and returns the violations if exits
     * </p>
     *
     * @return json array of violations if exists, null otherwise
     */
    public <T> JsonObject validate(final T object, final Class<?> groups) {
        final Set<ConstraintViolation<T>> violationSet = getValidator().validate(object, groups);
        final JsonObject jsonObject = jsonFactory.createObjectNode();

        if (violationSet.isEmpty()) {
            return jsonObject;
        }

        for (final ConstraintViolation<T> violation : violationSet) {
            jsonObject.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return jsonObject;
    }
}