package org.cine.booker.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import org.cine.booker.service.MovieService;
import org.cine.booker.service.internal.impl.MovieServiceImpl;
import org.cine.booker.model.movie.filter.MovieFilter;

/**
 * <p>
 * This class is responsible for handling movie information retrieval, filtering movie-related operations
 * and processing input received through a REST API.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Path("/movie")
public final class MovieController {

    private final MovieService movieService;

    private MovieController() {
        movieService = MovieServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieController MOVIE_CONTROLLER = new MovieController();
    }

    /**
     * <p>
     * Gets the instance of movie controller object.
     * </p>
     *
     * @return The movie controller object
     */
    public static MovieController getInstance() {
        return InstanceHolder.MOVIE_CONTROLLER;
    }

    /**
     * <p>
     *  Gets all the available movies.
     * </p>
     *
     * @return The list of movies
     */
    @GET
    @Produces("application/json")
    public byte[] getMovies() {
        return movieService.getMovies().asBytes();
    }

    /**
     * <p>
     *  Gets all the filtered movies.
     * </p>
     *
     * @return The list of filtered movies
     */
    @Path("/filtered")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] getFilteredMovies(final MovieFilter movieFilter) {
        return movieService.getFilteredMovies(movieFilter).asBytes();
    }

    /**
     * <p>
     *  Gets all the available filters.
     * </p>
     *
     * @return The list of filters
     */
    @Path("/filters")
    @GET
    @Produces("application/json")
    public byte[] getFilters() {
        return movieService.getFilters().asBytes();
    }

    /**
     * <p>
     * Gets the given filter values
     * </p>
     *
     * @param filterType Represents the type of filter
     * @return The List of filter values
     */
    @Path("filters/{filterType}")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] getFilterValues(@PathParam("filterType") final String filterType) {
        return movieService.getFilterValues(filterType).asBytes();
    }
}
