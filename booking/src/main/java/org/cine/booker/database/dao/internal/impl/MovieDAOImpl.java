package org.cine.booker.database.dao.internal.impl;

import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.cine.booker.database.QueryBuilder.QueryBuilder;
import org.cine.booker.database.dao.MovieDAO;
import org.cine.booker.database.resultsetextractor.MovieResultSetExtractor;
import org.cine.database.connection.DataBaseConnection;
import org.cine.booker.exception.MovieNotFoundException;
import org.cine.booker.model.movie.Movie;
import org.cine.booker.model.movie.filter.FilterConfig;
import org.cine.booker.model.movie.filter.MovieFilter;

/**
 * <p>
 *  Implements the database service for movie-related operations
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieDAOImpl implements MovieDAO {

    private final MovieResultSetExtractor movieResultSetExtractor;
    private final QueryBuilder queryBuilder;
    private final Connection connection;

    private MovieDAOImpl() {
        movieResultSetExtractor = MovieResultSetExtractor.getInstance();
        queryBuilder = QueryBuilder.getInstance();
        connection = DataBaseConnection.get();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieDAO MOVIE_DAO = new MovieDAOImpl();
    }

    /**
     * <p>
     * Gets the object of the movie database implementation class.
     * </p>
     *
     * @return The movie database service implementation object
     */
    public static MovieDAO getInstance() {
        return InstanceHolder.MOVIE_DAO;
    }

    /**
     * {@inheritDoc}
     *
     * @return List of movies
     */
    public Optional<Collection<Movie>> getMovies() {
        final String query = """
                select name, description, ratings, votes, status from movie
                 where status = 1 or release_date > current_date""";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return movieResultSetExtractor.getMovies(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MovieNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param movieFilter Represents the movie filter instance that holds the filter conditions
     * @return List of filtered movies
     */
    public Optional<Collection<Movie>> getFilteredMovies(final MovieFilter movieFilter) {
        final String query = queryBuilder.buildMovieFilterQuery(movieFilter);

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return movieResultSetExtractor.getMovies(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MovieNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return List of filters for movies
     */
    @Override
    public Optional<Collection<String>> getFilters() {
        final String query = "select name from filter group by name";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return movieResultSetExtractor.getFilters(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MovieNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param filterType Represents the type of filter
     * @return List of filter values
     */
    public Optional<FilterConfig> getFilterValues(final String filterType) {
        final String query = "select type from filter where name = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, filterType);
            return movieResultSetExtractor.getFilterValues(preparedStatement.executeQuery(), filterType);
        } catch (SQLException message) {
            throw new MovieNotFoundException(message.getMessage());
        }
    }
}
