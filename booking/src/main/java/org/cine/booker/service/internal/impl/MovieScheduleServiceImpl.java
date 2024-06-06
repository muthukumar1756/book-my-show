package org.cine.booker.service.internal.impl;

import java.util.Collection;
import java.util.Optional;

import org.cine.booker.database.dao.MovieScheduleDAO;
import org.cine.booker.database.dao.internal.impl.MovieScheduleDAOImpl;
import org.cine.booker.service.MovieScheduleService;
import org.cine.booker.model.seat.SeatAvailability;
import org.cine.booker.model.Movieschedule.MovieSchedule;
import org.cine.common.hibernate.HibernateEntityValidator;
import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonFactory;
import org.cine.common.json.JsonObject;
import org.cine.common.hibernate.impl.HibernateEntityValidatorImpl;
import org.cine.common.hibernate.validatorgroup.seat.GetSeatAvailabilityValidator;

/**
 * <p>
 * Implements a service for retrieving movie schedule and seat availability information.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieScheduleServiceImpl implements MovieScheduleService {

    private static final String STATUS = "status";
    private final MovieScheduleDAO movieScheduleDAO;
    private final JsonFactory jsonFactory;
    private final HibernateEntityValidator validatorFactory;

    private MovieScheduleServiceImpl() {
        movieScheduleDAO = MovieScheduleDAOImpl.getInstance();
        jsonFactory = JsonFactory.getInstance();
        validatorFactory = HibernateEntityValidatorImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieScheduleService MOVIE_SCHEDULE_SERVICE = new MovieScheduleServiceImpl();
    }

    /**
     * <p>
     * Gets the instance for movie schedule service implementation class.
     * </p>
     *
     * @return The movie schedule service implementation object
     */
    public static MovieScheduleService getInstance() {
        return InstanceHolder.MOVIE_SCHEDULE_SERVICE;
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of movie schedules
     */
    @Override
    public JsonArray getAllMovieSchedule() {
        final Optional<Collection<MovieSchedule>> movieScheduleList = movieScheduleDAO.getAll();
        final JsonArray jsonArray = jsonFactory.createArrayNode();

        return movieScheduleList.isPresent() ? jsonArray.build(movieScheduleList.get()) :
                jsonArray.add(jsonFactory.createObjectNode().put(STATUS, "Movie schedule not found"));
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of seat information
     */
    @Override
    public JsonObject getSeatInfo(final long movieScheduleId) {
        final MovieSchedule movieSchedule = new MovieSchedule(movieScheduleId);
        final JsonObject jsonObject = validatorFactory.validate(movieSchedule, GetSeatAvailabilityValidator.class);

        if (jsonObject.isEmpty()) {
            final Optional<SeatAvailability> seatAvailability = movieScheduleDAO.getSeatInfo(movieScheduleId);

            return seatAvailability.isPresent() ? jsonObject.build(seatAvailability.get()) :
                    jsonObject.put(STATUS, "Seat info not found");
        }

        return jsonObject;
    }
}