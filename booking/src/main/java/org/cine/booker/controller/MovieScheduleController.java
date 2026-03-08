package org.cine.booker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/schedule")
public class MovieScheduleController {

    private final MovieScheduleService movieScheduleService;

    public MovieScheduleController() {
        movieScheduleService = MovieScheduleServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Gets all the movie schedules.
     * </p>
     *
     * @return The list of movie schedules
     */
    @GetMapping(value = "/shows", produces = "application/json")
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
    @GetMapping(value = "/{movieScheduleId}", produces = "application/json")
    public byte[] getAvailableSeats(@PathVariable("movieScheduleId") final long movieScheduleId) {
        return movieScheduleService.getSeatInfo(movieScheduleId).asBytes();
    }
}
