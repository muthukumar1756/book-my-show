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
import org.cine.booker.model.theatre.Theatre;
import org.cine.booker.model.ticket.Ticket;

/**
 * <p>
 *  Handles the result set and returns the instances
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieTicketBookingResultSetExtractor {

    private static final Logger LOGGER = LogManager.getLogger(MovieTicketBookingResultSetExtractor.class);

    private MovieTicketBookingResultSetExtractor() {
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieTicketBookingResultSetExtractor TICKET_BOOKING_RESULT_SET_HANDLER = new MovieTicketBookingResultSetExtractor();
    }

    /**
     * <p>
     * Gets the instance of the ticket booking result set extractor class.
     * </p>
     *
     * @return The ticket booking result set extractor object
     */
    public static MovieTicketBookingResultSetExtractor getInstance() {
        return MovieTicketBookingResultSetExtractor.InstanceHolder.TICKET_BOOKING_RESULT_SET_HANDLER;
    }

    /**
     * <p>
     *  Get the ticket instance.
     * </p>
     *
     * @return The ticket instance
     */
    public Optional<Ticket> getTicket(final ResultSet resultSet, final Collection<String> seatList) {
        try {

            if (resultSet.next()) {
                final Movie movie = new Movie.MovieBuilder().setId(resultSet.getLong(1))
                        .setName(resultSet.getString(2))
                        .setLanguage(LanguageType.getTypeById(resultSet.getInt(3)).get().name()).build();

                final Screen screen = new Screen.ScreenBuilder().setId(resultSet.getLong(4))
                        .setName(resultSet.getString(5)).setMovie(movie)
                        .setShowDate(resultSet.getDate(6).toString())
                        .setShowTime(resultSet.getTime(7).toString()).build();

                final Theatre theatre = new Theatre.TheatreBuilder().setId(resultSet.getLong(8))
                        .setName(resultSet.getString(9)).setScreen(screen).build();

                final Ticket ticket = new Ticket.TicketBuilder().setId(resultSet.getLong(10))
                        .setSeatCount(resultSet.getInt(11)).setAmountPaid(resultSet.getFloat(12))
                        .setTheatre(theatre).setSeatList(seatList).build();

                return Optional.of(ticket);
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }

    /**
     * <p>
     *  Get the booked seat name list.
     * </p>
     *
     * @return List of booked seat names
     */
    public Optional<Collection<String>> getSeatNameList(final ResultSet resultSet) {
        try {

            if (resultSet.isBeforeFirst()) {
                final Collection<String> seatNameList = new ArrayList<>();

                while(resultSet.next()) {
                    seatNameList.add(resultSet.getString(1));
                }

                return Optional.of(Collections.unmodifiableCollection(seatNameList));
            }

            return Optional.empty();
        } catch (SQLException message) {
            LOGGER.error(message.getMessage());
            throw new TicketBookingFailedException(message.getMessage());
        }
    }
}