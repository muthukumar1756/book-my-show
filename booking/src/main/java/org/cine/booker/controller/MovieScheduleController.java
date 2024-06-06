package org.cine.booker.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.cine.booker.service.MovieScheduleService;
import org.cine.booker.service.internal.impl.MovieScheduleServiceImpl;

/**
 * <p>
 * This class manages operations related to retrieving movie schedule information.
 * It's responsible for receiving input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Path("/schedule")
public final class MovieScheduleController {

    private final MovieScheduleService movieScheduleService;

    private MovieScheduleController() {
        movieScheduleService = MovieScheduleServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieScheduleController MOVIE_SCHEDULE_CONTROLLER = new MovieScheduleController();
    }

    /**
     * <p>
     * Gets the instance movie schedule controller.
     * </p>
     *
     * @return The movie schedule controller object
     */
    public static MovieScheduleController getInstance() {
        return InstanceHolder.MOVIE_SCHEDULE_CONTROLLER;
    }

    /**
     * <p>
     *  Gets all the movie schedules.
     * </p>
     *
     * @return The list of movie schedules
     */
    @Path("/shows")
    @GET
    @Produces("application/json")
    public byte[] getAll() {
        return movieScheduleService.getAllMovieSchedule().asBytes();
    }

    /**
     * <p>
     *  Gets the seat availability.
     * </p>
     *
     * @return The list of seat information
     */
    @Path("/{movieScheduleId}")
    @GET
    @Produces("application/json")
    public byte[] getAvailableSeats(@PathParam("movieScheduleId") final long movieScheduleId) {
        return movieScheduleService.getSeatInfo(movieScheduleId).asBytes();
    }
}
