package org.cine.booker.database.resultsetextractor;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.cine.booker.exception.TicketBookingFailedException;
import org.cine.booker.model.movie.Movie;
import org.cine.booker.model.movie.MovieStatus;
import org.cine.booker.model.movie.filter.FilterConfig;
import org.cine.booker.model.movie.filter.FormatType;
import org.cine.booker.model.movie.filter.GenreType;
import org.cine.booker.model.movie.filter.LanguageType;

/**
 * <p>
 *  Handles the result set and returns the instances
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieResultSetExtractor {

    private static final Logger LOGGER = LogManager.getLogger(MovieResultSetExtractor.class);

    private MovieResultSetExtractor() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieResultSetExtractor MOVIE_RESULT_SET_HANDLER = new MovieResultSetExtractor();
    }

    /**
     * <p>
     * Gets the instance of the movie result set extractor class.
     * </p>
     *
     * @return The movie result set extractor object
     */
    public static MovieResultSetExtractor getInstance() {
        return InstanceHolder.MOVIE_RESULT_SET_HANDLER;
    }

    /**
     * <p>
     * Gets all the movies
     * </p>
     *
     * @return List of  movies
     */
    public Optional<Collection<Movie>> getMovies(final ResultSet resultSet) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Collection<Movie> movies = new ArrayList<>();

                while (resultSet.next()) {
                    final Movie movie = new Movie.MovieBuilder().setName(resultSet.getString(1))
                            .setDescription(resultSet.getString(2))
                            .setRating(resultSet.getFloat(3)).setVotes(resultSet.getLong(4))
                            .setStatus(MovieStatus.getTypeById(resultSet.getInt(5)).get())
                            .build();

                    movies.add(movie);
                }

                return Optional.of(Collections.unmodifiableCollection(movies));
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Gets the given filter values
     * </p>
     *
     * @param filterType Represents the type of filter
     * @return List of filter values
     */
    public Optional<FilterConfig> getFilterValues(final ResultSet resultSet, final String filterType) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Map<Integer, String> filterValues = new HashMap<>();

                while (resultSet.next()) {
                    final Integer filterId = resultSet.getInt(1);

                    switch (filterType) {
                        case "language" -> filterValues.put(filterId, LanguageType.getTypeById(filterId).get().name());
                        case "format" -> filterValues.put(filterId, FormatType.getTypeById(filterId).get().name());
                        case "genre" -> filterValues.put(filterId, GenreType.getTypeById(filterId).get().name());
                    }
                }
                final FilterConfig filterConfig = new FilterConfig(filterType, Collections.unmodifiableMap(filterValues));

                return Optional.of(filterConfig);
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Gets all the available filters
     * </p>
     *
     * @return List of filters
     */
    public Optional<Collection<String>> getFilters(final ResultSet resultSet) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Collection<String> filterList = new ArrayList<>();

                while (resultSet.next()) {
                    filterList.add(resultSet.getString(1));
                }

                return Optional.of(Collections.unmodifiableCollection(filterList));
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }
}
