package org.cine.booker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/book")
public class MovieTicketBookingController {

    private final MovieTicketBookingService movieTicketBookingService;

    public MovieTicketBookingController() {
        movieTicketBookingService = MovieTicketBookingServiceImpl.getInstance();
    }

    /**
     * <p>
     *   Books and gets the ticket for the show
     * </p>
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    @GetMapping(value = "/ticket", consumes = "application/json", produces = "application/json")
    public byte[] bookTickets(@RequestBody final TicketRequest ticketRequest) {
        return movieTicketBookingService.bookTickets(ticketRequest).asBytes();
    }
}