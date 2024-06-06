package org.cine.booker.database.dao;

import java.util.Optional;

import org.cine.booker.model.ticket.Ticket;
import org.cine.booker.model.ticket.TicketRequest;

/**
 * <p>
 * Provides data base service for the booking tickets
 * </p>
 *
 * @author Muthu kumar v
 * @version 1.1
 */
public interface MovieTicketBookingDAO {

    /**
     * <p>
     *   Books and gets the tickets of the movie.
     * </p>
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    Optional<Ticket> bookTickets(final TicketRequest ticketRequest);
}
