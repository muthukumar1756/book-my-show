package org.cine.common.hibernate;

import org.cine.common.json.JsonObject;

/**
 * <p>
 * Handles the method for the hibernate validation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface HibernateEntityValidator {

    <T> JsonObject validate(final T object, final Class<?> groups);
}
