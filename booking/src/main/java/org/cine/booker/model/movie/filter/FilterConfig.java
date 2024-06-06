package org.cine.booker.model.movie.filter;

import java.util.Map;

/**
 * <p>
 * Represents filter details entity of the movies with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public record FilterConfig(String filterName, Map<Integer, String> filterInfo) {
}