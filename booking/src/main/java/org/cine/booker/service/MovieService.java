package org.cine.booker.service;

import org.cine.booker.model.movie.filter.MovieFilter;
import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonObject;

/**
 * <p>
 * Service methods for the displaying movie information related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface MovieService {

    /**
     * <p>
     * Gets all the movies
     * </p>
     *
     * @return The list of  movies
     */
    JsonArray getMovies();

    /**
     * <p>
     * Gets the filtered movies based on the given filters
     * </p>
     *
     * @param movieFilter Represents the movie filter instance that holds the filter conditions
     * @return The list of filtered movies
     */
    JsonArray getFilteredMovies(final MovieFilter movieFilter);

    /**
     * <p>
     * Gets all the available filters
     * </p>
     *
     * @return The list of filters
     */
    JsonArray getFilters();

    /**
     * <p>
     * Gets the given filter values
     * </p>
     *
     * @param filterType Represents the type of filter
     * @return The list of filter values
     */
    JsonObject getFilterValues(final String filterType);
}
