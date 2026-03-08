package org.cine.booker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController() {
        movieService = MovieServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Gets all the available movies.
     * </p>
     *
     * @return The list of movies
     */
    @GetMapping(produces = "application/json")
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
    @GetMapping(value = "/filtered", consumes = "application/json", produces = "application/json")
    public byte[] getFilteredMovies(@RequestBody final MovieFilter movieFilter) {
        return movieService.getFilteredMovies(movieFilter).asBytes();
    }

    /**
     * <p>
     *  Gets all the available filters.
     * </p>
     *
     * @return The list of filters
     */
    @GetMapping(value = "/filters", produces = "application/json")
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
    @GetMapping(value = "/filters/{filterType}", consumes = "application/json", produces = "application/json")
    public byte[] getFilterValues(@PathVariable("filterType") final String filterType) {
        return movieService.getFilterValues(filterType).asBytes();
    }
}
