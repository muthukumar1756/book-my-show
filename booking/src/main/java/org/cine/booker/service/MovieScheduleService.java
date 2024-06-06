package org.cine.booker.service;

import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonObject;

/**
 * <p>
 * Implements a service for displaying show schedule related operations.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface MovieScheduleService {

    /**
     * <p>
     *  Gets all the movie schedules.
     * </p>
     *
     * @return The list of movie schedules
     */
    JsonArray getAllMovieSchedule();

    /**
     * <p>
     *  Gets seat availability information of the given movie schedule id.
     * </p>
     *
     * @return The list of seat information
     */
    JsonObject getSeatInfo(final long movieScheduleId);
}