package org.cine.booker.service;

import org.cine.booker.model.ticket.TicketRequest;
import org.cine.common.json.JsonArray;
import org.cine.common.json.JsonObject;

/**
 * <p>
 * Service methods for the ticket booking related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public interface MovieTicketBookingService {

    /**
     * <p>
     *   Books and gets the ticket for the show
     * </p>
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    JsonObject bookTickets(final TicketRequest ticketRequest);
}
