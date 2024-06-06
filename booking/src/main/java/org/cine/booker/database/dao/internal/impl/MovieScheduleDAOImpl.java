package org.cine.booker.database.dao.internal.impl;

import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.cine.booker.database.dao.MovieScheduleDAO;
import org.cine.booker.database.resultsetextractor.MovieScheduleResultSetExtractor;
import org.cine.database.connection.DataBaseConnection;
import org.cine.booker.exception.MovieScheduleNotFoundException;
import org.cine.booker.model.seat.Seat;
import org.cine.booker.model.seat.SeatAvailability;
import org.cine.booker.model.Movieschedule.MovieSchedule;

/**
 * <p>
 * Implements the database service for movie schedule related operations.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieScheduleDAOImpl implements MovieScheduleDAO {

    private final MovieScheduleResultSetExtractor movieScheduleResultSetExtractor;
    private final Connection connection;

    private MovieScheduleDAOImpl() {
        movieScheduleResultSetExtractor = MovieScheduleResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieScheduleDAO MOVIE_SCHEDULE_DAO = new MovieScheduleDAOImpl();
    }

    /**
     * <p>
     * Gets the instance of the movie schedule database implementation class.
     * </p>
     *
     * @return The movie schedule database service implementation object
     */
    public static MovieScheduleDAO getInstance() {
        return InstanceHolder.MOVIE_SCHEDULE_DAO;
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of movie schedules
     */
    @Override
    public Optional<Collection<MovieSchedule>> getAll() {
        final String query = """
                select m.id, m.name, ss.language, s.id, s.name, ss.show_date,
                ss.show_starting_time, t.id, t.name, ss.id
                from show_schedule ss
                join screen s on ss.screen_id = s.id
                join theatre_screen ts on s.id = ts.screen_id
                join theatre t on ts.theatre_id = t.id
                join movie m on ss.movie_id = m.id""";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return movieScheduleResultSetExtractor.getAllMovieSchedule(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MovieScheduleNotFoundException(message.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return The list of seat information
     */
    public Optional<SeatAvailability> getSeatInfo(final long movieScheduleId) {
        final String query = """
                select st.id, st.name, sa.status, sc.category, tp.rate, sc.seating_type from
                show_schedule ss
                join ticket_pricing tp on ss.id = tp.show_schedule_id
                join seat_category sc on tp.seat_category_id = sc.id
                join seat st on sc.id = st.seat_category_id
                join seat_availability sa on ss.id = sa.show_schedule_id and st.id = sa.seat_id
                where ss.id = ?""";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movieScheduleId);
            final SeatAvailability seatAvailability = getSeatCountInfo(movieScheduleId).get();
            final Collection<Seat> seatList = movieScheduleResultSetExtractor.getSeatInfo(preparedStatement.executeQuery()).get();

            seatAvailability.setSeatList(seatList);

            return Optional.of(seatAvailability);
        } catch (SQLException message) {
            throw new MovieScheduleNotFoundException(message.getMessage());
        }
    }

    /**
     * <p>
     *  Gets the seat count details of the show.
     * </p>
     *
     * @return The seat availability instance
     */
    private Optional<SeatAvailability> getSeatCountInfo(final Long movieScheduleId) {
        final String query = """
                select
                sum(case when status = 1 then 1 else 0 end),
                sum(case when status = 2 then 1 else 0 end),
                count(seat_id)
                from seat_availability
                where show_schedule_id = ?""";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movieScheduleId);

            return movieScheduleResultSetExtractor.getSeatCountInfo(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new MovieScheduleNotFoundException(message.getMessage());
        }
    }
}