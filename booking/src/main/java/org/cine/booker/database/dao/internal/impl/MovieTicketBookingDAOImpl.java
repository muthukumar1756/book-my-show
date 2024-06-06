package org.cine.booker.database.dao.internal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cine.booker.database.resultsetextractor.MovieTicketBookingResultSetExtractor;
import org.cine.booker.database.dao.MovieTicketBookingDAO;
import org.cine.booker.exception.TicketBookingFailedException;
import org.cine.booker.model.ticket.Ticket;
import org.cine.booker.model.ticket.TicketRequest;
import org.cine.database.connection.DataBaseConnection;

/**
 * <p>
 * Implements the data base service of the ticket booking related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieTicketBookingDAOImpl implements MovieTicketBookingDAO {

    private final MovieTicketBookingResultSetExtractor movieTicketBookingResultSetExtractor;
    private final Connection connection;

    private MovieTicketBookingDAOImpl() {
        movieTicketBookingResultSetExtractor = MovieTicketBookingResultSetExtractor.getInstance();
        connection = DataBaseConnection.get();
    }

    /**
     * <p>
     * Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieTicketBookingDAO MOVIE_TICKET_BOOKING_DAO = new MovieTicketBookingDAOImpl();
    }

    /**
     * <p>
     * Gets the instance of the ticket booking database service implementation class.
     * </p>
     *
     * @return The booking database service implementation object
     */
    public static MovieTicketBookingDAO getInstance() {
        return InstanceHolder.MOVIE_TICKET_BOOKING_DAO;
    }

    /**
     * {@inheritDoc}
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    @Override
    public Optional<Ticket> bookTickets(final TicketRequest ticketRequest) {
        try {
            connection.setAutoCommit(false);
            final String query = """
                    insert into ticket (show_schedule_id, seat_count, total_amount_paid)
                    values (?, ?, ?) returning id""";

            if (isSeatsAvailable(ticketRequest.getSeatIdList(), ticketRequest.getMovieScheduleId()) &&
                    updateSeats(ticketRequest.getSeatIdList(), ticketRequest.getMovieScheduleId())) {
                final TicketRequest ticketRequestInfo = getTicketRequestInfo(ticketRequest.getSeatIdList(),
                        ticketRequest.getMovieScheduleId()).get();

                try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setLong(1, ticketRequest.getMovieScheduleId());
                    preparedStatement.setInt(2, ticketRequestInfo.getSeatCount());
                    preparedStatement.setFloat(3, ticketRequestInfo.getAmountPaid());
                    final ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        final Long ticketId = resultSet.getLong(1);

                        mapBookedSeatNames(ticketId, ticketRequestInfo.getSeatNameList());
                        final Optional<Ticket> ticket = getTicket(ticketId);

                        updateBooking(ticketRequest.getUserId(), ticketId);
                        connection.commit();

                        return ticket;
                    }
                } catch (SQLException message) {
                    connection.rollback();
                    throw new TicketBookingFailedException(message.getMessage());
                } finally {
                    connection.setAutoCommit(true);
                }
            }

            return Optional.empty();
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Checks the selected seats are available
     * </p>
     *
     * @param seatList The list of seats to be booked
     * @return True if all the seats are available, false otherwise
     */
    private boolean isSeatsAvailable(final Collection<Long> seatList, final Long movieScheduleId) {
        final String query = "select status from seat_availability where seat_id = ? and show_schedule_id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (final Long seatId : seatList) {
                preparedStatement.setLong(1, seatId);
                preparedStatement.setLong(2, movieScheduleId);
                final ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.next();

                if (resultSet.getInt(1) != 1) {
                    return false;
                }
            }

            return true;
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Updates the status of the selected seats
     * </p>
     *
     * @param seatList list of seats to be booked
     * @return True if the all the seats are updated, false otherwise
     */
    private boolean updateSeats(final Collection<Long> seatList, final Long movieScheduleId) {
        final String query = "update seat_availability set status = 2 where seat_id = ? and show_schedule_id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (final Long seatId : seatList) {
                preparedStatement.setLong(1, seatId);
                preparedStatement.setLong(2, movieScheduleId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            return true;
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Gets the information about the ticket request
     * </p>
     *
     * @param seatList       Represents the list of seats to be booked
     * @param movieScheduleId Represents the id of the show scheduled
     * @return The ticket request instance
     */
    private Optional<TicketRequest> getTicketRequestInfo(final Collection<Long> seatList, final Long movieScheduleId) {
        final String query = """
                select st.name, tp.rate
                from ticket_pricing tp
                join seat_category sc on tp.seat_category_id = sc.id
                join seat st on sc.id = st.seat_category_id
                where st.id = ? and tp.show_schedule_id = ?""";
        final Collection<String> seatNameList = new ArrayList<>();
        Float total = 0F;

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (final Long seatId : seatList) {
                preparedStatement.setLong(1, seatId);
                preparedStatement.setLong(2, movieScheduleId);
                final ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    seatNameList.add(resultSet.getString(1));
                    total += resultSet.getFloat(2);
                }
            }
            final TicketRequest ticketRequest = new TicketRequest.TicketRequestBuilder().setSeatNameList(seatNameList)
                    .setSeatCount(seatList.size()).setAmountPaid(total).build();

            return Optional.of(ticketRequest);
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Maps the booked seat names with the ticket id.
     * </p>
     *
     * @param ticketId Represents the id of the ticket booked
     * @param seatList Represents the booked list of seat names
     */
    private void mapBookedSeatNames(final Long ticketId, final Collection<String> seatList) {
        final String query = "insert into ticket_seats (ticket_id, seat_name) values (?, ?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (final String seatName : seatList) {
                preparedStatement.setLong(1, ticketId);
                preparedStatement.setString(2, seatName);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Creates the entry in the booking table.
     * </p>
     *
     * @param userId   Represents the id of the user
     * @param ticketId Represents the id of the booked ticket
     */
    private void updateBooking(final Long userId, final Long ticketId) {
        final String query = "insert into bookings (user_id, ticket_id) values (?, ?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, ticketId);
            preparedStatement.executeUpdate();
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Get the ticket instance.
     * </p>
     *
     * @param ticketId Represents the id of the ticket
     * @return The ticket instance
     */
    private Optional<Ticket> getTicket(final Long ticketId) {
        final String query = """
                select m.id, m.name, ss.language, s.id, s.name, ss.show_date, ss.show_starting_time, t.id, t.name,
                tckt.id, tckt.seat_count, tckt.total_amount_paid from ticket tckt
                join show_schedule ss on tckt.show_schedule_id = ss.id
                join screen s on ss.screen_id = s.id
                join theatre_screen ts on s.id = ts.screen_id
                join theatre t on ts.theatre_id = t.id
                join movie m on ss.movie_id = m.id
                where tckt.id = ?""";
        final Collection<String> seatList = getSeatNameList(ticketId).get();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, ticketId);

            return movieTicketBookingResultSetExtractor.getTicket(preparedStatement.executeQuery(), seatList);
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     * Get the booked seat name list.
     * </p>
     *
     * @param ticketId Represents the id of the booked ticket
     * @return List of booked seat names
     */
    private Optional<Collection<String>> getSeatNameList(final Long ticketId) {
        final String query = "select seat_name from ticket_seats where ticket_id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, ticketId);

            return movieTicketBookingResultSetExtractor.getSeatNameList(preparedStatement.executeQuery());
        } catch (SQLException message) {
            throw new TicketBookingFailedException(message.getMessage());
        }
    }
}