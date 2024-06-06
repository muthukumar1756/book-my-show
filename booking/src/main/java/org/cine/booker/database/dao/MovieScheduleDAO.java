package org.cine.booker.database.dao;

import java.util.Collection;
import java.util.Optional;

import org.cine.booker.model.seat.SeatAvailability;
import org.cine.booker.model.Movieschedule.MovieSchedule;

/**
 * <p>
 * Provides data base service for the getting information about movie schedule.
 * </p>
 *
 * @author Muthu kumar v
 * @version 1.1
 */
public interface MovieScheduleDAO {

    /**
     * <p>
     *  Gets all the movie schedules.
     * </p>
     *
     * @return The list of movie schedules
     */
    Optional<Collection<MovieSchedule>> getAll();

    /**
     * <p>
     *  Gets seat availability for the given movie schedule id.
     * </p>
     *
     * @return The list of seat information
     */
    Optional<SeatAvailability> getSeatInfo(final long movieScheduleId);
}
