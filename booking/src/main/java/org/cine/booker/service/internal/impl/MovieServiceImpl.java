package org.cine.booker.service.internal.impl;

import java.util.Collection;
import java.util.Optional;

import org.cine.booker.database.dao.MovieDAO;
import org.cine.booker.database.dao.internal.impl.MovieDAOImpl;
import org.cine.booker.service.MovieService;
import org.cine.booker.model.movie.Movie;
import org.cine.booker.model.movie.filter.FilterConfig;
import org.cine.booker.model.movie.filter.MovieFilter;
import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonFactory;
import org.cine.common.json.JsonObject;

/**
 * <p>
 * Implements the service of the movie related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public class MovieServiceImpl implements MovieService {

    private static final String STATUS = "status";
    private final JsonFactory jsonFactory;
    private final MovieDAO movieDAO;

    private MovieServiceImpl() {
        movieDAO = MovieDAOImpl.getInstance();
        jsonFactory = JsonFactory.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieService MOVIE_SERVICE = new MovieServiceImpl();
    }

    /**
     * <p>
     * Gets the movie service implementation object.
     * </p>
     *
     * @return The movie service implementation object
     */
    public static MovieService getInstance() {
        return InstanceHolder.MOVIE_SERVICE;
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of movies
     */
    @Override
    public JsonArray getMovies() {
        final Optional<Collection<Movie>> movies = movieDAO.getMovies();
        final JsonArray jsonArray = jsonFactory.createArrayNode();

        return movies.isPresent() ? jsonArray.build(movies.get()) :
                jsonArray.add(jsonFactory.createObjectNode().put(STATUS, "Movies not found"));
    }

    /**
     * {@inheritDoc}
     *
     * @param movieFilter Represents the movie filter instance that holds the filter conditions
     * @return The list of filtered movies
     */
    @Override
    public JsonArray getFilteredMovies(final MovieFilter movieFilter) {
        final Optional<Collection<Movie>> movies = movieDAO.getFilteredMovies(movieFilter);
        final JsonArray jsonArray = jsonFactory.createArrayNode();

        return movies.isPresent() ? jsonArray.build(movies.get()) : jsonArray.add(jsonFactory.createObjectNode()
                .put(STATUS, "No movies available in the applied filters"));
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of filters
     */
    @Override
    public JsonArray getFilters() {
        final Optional<Collection<String>> filters = movieDAO.getFilters();
        final JsonArray jsonArray = jsonFactory.createArrayNode();

        return filters.isPresent() ? jsonArray.build(filters.get()) :
                jsonArray.add(jsonFactory.createObjectNode().put(STATUS, "Filters not found"));
    }

    /**
     * {@inheritDoc}
     *
     * @param filterType Represents the type of filter
     * @return The list of filter values
     */
    @Override
    public JsonObject getFilterValues(final String filterType) {
        final Optional<FilterConfig> filters = movieDAO.getFilterValues(filterType);
        final JsonObject jsonObject = jsonFactory.createObjectNode();

        return filters.isPresent() ? jsonObject.build(filters.get()) :
                jsonObject.put(STATUS, "Filter type not found");
    }
}
