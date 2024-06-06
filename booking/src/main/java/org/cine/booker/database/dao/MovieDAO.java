package org.cine.booker.database.dao;

import java.util.Collection;
import java.util.Optional;

import org.cine.booker.model.movie.Movie;
import org.cine.booker.model.movie.filter.FilterConfig;
import org.cine.booker.model.movie.filter.MovieFilter;

/**
 * <p>
 * Provides a database service for retrieving information about movies.
 * </p>
 *
 * @author Muthu kumar v
 * @version 1.1
 */
public interface MovieDAO {

    /**
     * <p>
     * Gets all the movies
     * </p>
     *
     * @return List of  movies
     */
    Optional<Collection<Movie>> getMovies();

    /**
     * <p>
     * Gets the filtered movies based on the given filters
     * </p>
     *
     * @param movieFilter Represents the movie filter instance that holds the filter conditions
     * @return List of filtered movies
     */
    Optional<Collection<Movie>> getFilteredMovies(final MovieFilter movieFilter);

    /**
     * <p>
     * Gets all the available filters
     * </p>
     *
     * @return List of filters
     */
    Optional<Collection<String>> getFilters();

    /**
     * <p>
     * Gets the given filter values
     * </p>
     *
     * @param filterType Represents the type of filter
     * @return List of filter values
     */
    Optional<FilterConfig> getFilterValues(final String filterType);
}
