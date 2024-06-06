package org.cine.booker.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import org.cine.booker.service.MovieTicketBookingService;
import org.cine.booker.service.internal.impl.MovieTicketBookingServiceImpl;
import org.cine.booker.model.ticket.TicketRequest;

/**
 * <p>
 * This class handles ticket booking operations. It's responsible for receiving input through a REST API and processing it.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
@Path("/book")
public final class MovieTicketBookingController {

    private final MovieTicketBookingService movieTicketBookingService;

    private MovieTicketBookingController() {
        movieTicketBookingService = MovieTicketBookingServiceImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieTicketBookingController MOVIE_TICKET_BOOKING_CONTROLLER = new MovieTicketBookingController();
    }

    /**
     * <p>
     * Gets the instance of movie ticket booking controller.
     * </p>
     *
     * @return The movie ticket booking controller object
     */
    public static MovieTicketBookingController getInstance() {
        return InstanceHolder.MOVIE_TICKET_BOOKING_CONTROLLER;
    }

    /**
     * <p>
     *   Books and gets the ticket for the show
     * </p>
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    @Path("/ticket")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public byte[] bookTickets(final TicketRequest ticketRequest) {
        return movieTicketBookingService.bookTickets(ticketRequest).asBytes();
    }
}