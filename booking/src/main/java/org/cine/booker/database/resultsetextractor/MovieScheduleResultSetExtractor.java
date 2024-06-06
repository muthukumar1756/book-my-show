package org.cine.booker.database.resultsetextractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.cine.booker.exception.TicketBookingFailedException;
import org.cine.booker.model.movie.Movie;
import org.cine.booker.model.movie.filter.LanguageType;
import org.cine.booker.model.screen.Screen;
import org.cine.booker.model.seat.Seat;
import org.cine.booker.model.seat.SeatAvailability;
import org.cine.booker.model.seat.SeatStatus;
import org.cine.booker.model.seat.SeatingType;
import org.cine.booker.model.Movieschedule.MovieSchedule;
import org.cine.booker.model.theatre.Theatre;
import org.cine.booker.model.ticket.TicketCategory;

/**
 * <p>
 *  Handles the result set and returns the instances
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieScheduleResultSetExtractor {

    private static final Logger LOGGER = LogManager.getLogger(MovieScheduleResultSetExtractor.class);

    private MovieScheduleResultSetExtractor() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieScheduleResultSetExtractor MOVIE_SCHEDULE_RESULT_SET_EXTRACTOR = new MovieScheduleResultSetExtractor();
    }

    /**
     * <p>
     * Gets the instance of the show schedule result set extractor class.
     * </p>
     *
     * @return The show schedule result set extractor object
     */
    public static MovieScheduleResultSetExtractor getInstance() {
        return InstanceHolder.MOVIE_SCHEDULE_RESULT_SET_EXTRACTOR;
    }

    /**
     * <p>
     *  Gets all the show schedules.
     * </p>
     *
     * @return The list of shows schedules
     */
    public Optional<Collection<MovieSchedule>> getAllMovieSchedule(final ResultSet resultSet) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Collection<MovieSchedule> movieScheduleList = new ArrayList<>();

                while (resultSet.next()) {
                    final Movie movie = new Movie.MovieBuilder().setId(resultSet.getLong(1))
                            .setName(resultSet.getString(2))
                            .setLanguage(LanguageType.getTypeById(resultSet.getInt(3)).get().name()).build();

                    final Screen screen = new Screen.ScreenBuilder().setId(resultSet.getLong(4))
                            .setName(resultSet.getString(5)).setMovie(movie)
                            .setShowDate(resultSet.getDate(6).toString())
                            .setShowTime(resultSet.getTime(7).toString()).build();

                    final Theatre theatre = new Theatre.TheatreBuilder().setId(resultSet.getLong(8))
                            .setName(resultSet.getString(9)).setScreen(screen).build();

                    final MovieSchedule movieSchedule = new MovieSchedule(resultSet.getLong(10), theatre);

                    movieScheduleList.add(movieSchedule);
                }

                return Optional.of(Collections.unmodifiableCollection(movieScheduleList));
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     *  Gets seat availability for the given movie schedule id.
     * </p>
     *
     * @return The list of seat information
     */
    public Optional<Collection<Seat>> getSeatInfo(final ResultSet resultSet) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Collection<Seat> seatList = new ArrayList<>();

                while (resultSet.next()) {
                    final Seat seat = new Seat.SeatBuilder().setId(resultSet.getLong(1))
                            .setName(resultSet.getString(2))
                            .setStatus(SeatStatus.getTypeById(resultSet.getInt(3)).get())
                            .setTicketCategory(TicketCategory.getTypeById(resultSet.getInt(4)).get())
                            .setRate(resultSet.getFloat(5))
                            .setSeatingType(SeatingType.getTypeById(resultSet.getInt(6)).get()).build();

                    seatList.add(seat);
                }

                return Optional.of(Collections.unmodifiableCollection(seatList));
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     *  Gets the seat count details of the show.
     * </p>
     *
     * @return The seat availability instance
     */
    public Optional<SeatAvailability> getSeatCountInfo(final ResultSet resultSet) {
        try {

            if (resultSet.next()) {
                final SeatAvailability seatAvailability = new SeatAvailability.SeatAvailabilityBuilder()
                        .setAvailableSeats(resultSet.getInt(1))
                        .setBookedSeats(resultSet.getInt(2))
                        .setTotalSeatCount(resultSet.getInt(3)).build();

                return Optional.of(seatAvailability);
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }
}