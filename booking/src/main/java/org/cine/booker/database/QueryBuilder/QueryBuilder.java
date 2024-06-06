package org.cine.booker.database.QueryBuilder;

import java.util.Collection;
import java.util.Objects;

import org.cine.booker.model.movie.filter.MovieFilter;

/**
 * <p>
 * Builds the query with the given specifications
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class QueryBuilder {

    private QueryBuilder() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final QueryBuilder QUERY_BUILDER = new QueryBuilder();
    }

    /**
     * <p>
     * Gets the object of the query builder class.
     * </p>
     *
     * @return The query builder object
     */
    public static QueryBuilder getInstance() {
        return InstanceHolder.QUERY_BUILDER;
    }

    /**
     * <p>
     * Build the query that gets filtered movies.
     * </p>
     *
     * @param movieFilter Represents the movie filter instance that has the filter conditions
     * @return The query to be executed
     */
    public String buildMovieFilterQuery(final MovieFilter movieFilter) {
        final StringBuilder query = new StringBuilder();
        final Collection<Integer> languageFilter = movieFilter.languageIds();
        final Collection<Integer> formatFilters = movieFilter.formatIds();
        final Collection<Integer> genreFilters = movieFilter.genreIds();

        query.append("select distinct m.name, m.description, m.ratings, m.votes, m.status from  movie m");

        if (Objects.nonNull(languageFilter) && !languageFilter.isEmpty()) {
            query.append(" join movie_language ml on m.id = ml.movie_id and ml.language in ");
            appendValues(languageFilter, query);
        }

        if (Objects.nonNull(genreFilters) && !genreFilters.isEmpty()) {
            query.append(" join movie_genre mg on m.id = mg.movie_id and mg.genre in ");
            appendValues(genreFilters, query);
        }

        if (Objects.nonNull(formatFilters) && !formatFilters.isEmpty()) {
            query.append(" join movie_format mf on m.id = mf.movie_id and mf.format in ");
            appendValues(formatFilters, query);
        }
        query.append(" where m.status = 1 and m.release_date > current_date");

        return query.toString();
    }

    /**
     * <p>
     * Appends the field values in the query
     * </p>
     *
     * @param filterValues Represents the field values of the query
     */
    private void appendValues(final Collection<Integer> filterValues, final StringBuilder query) {
        query.append(" (");
        boolean isFirstField = false;

        for (final Integer filterId : filterValues) {

            if (isFirstField) {
                query.append(", ");
            } else {
                isFirstField = true;
            }
            query.append(filterId);
        }
        query.append(")");
    }
}
