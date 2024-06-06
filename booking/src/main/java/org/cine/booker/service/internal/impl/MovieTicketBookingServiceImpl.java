package org.cine.booker.service.internal.impl;

import java.util.Optional;

import org.cine.booker.database.dao.MovieTicketBookingDAO;
import org.cine.booker.database.dao.internal.impl.MovieTicketBookingDAOImpl;
import org.cine.booker.service.MovieTicketBookingService;
import org.cine.booker.model.ticket.Ticket;
import org.cine.booker.model.ticket.TicketRequest;
import org.cine.common.hibernate.HibernateEntityValidator;
import org.cine.common.json.JsonObject;
import org.cine.common.hibernate.impl.HibernateEntityValidatorImpl;
import org.cine.common.hibernate.validatorgroup.ticket.GetTicketValidator;

/**
 * <p>
 * Implements the service of the ticket booking related operation.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class MovieTicketBookingServiceImpl implements MovieTicketBookingService {

    private static final String STATUS = "status";
    private final MovieTicketBookingDAO movieTicketBookingDAO;
    private final HibernateEntityValidator validatorFactory;

    private MovieTicketBookingServiceImpl() {
        movieTicketBookingDAO = MovieTicketBookingDAOImpl.getInstance();
        validatorFactory = HibernateEntityValidatorImpl.getInstance();
    }

    /**
     * <p>
     *  Creates the instance of the class
     * </p>
     */
    private static class InstanceHolder {

        private static final MovieTicketBookingService BOOKING_SERVICE = new MovieTicketBookingServiceImpl();
    }

    /**
     * <p>
     * Gets the booking service implementation object.
     * </p>
     *
     * @return The booking service implementation object
     */
    public static MovieTicketBookingService getInstance() {
        return InstanceHolder.BOOKING_SERVICE;
    }

    /**
     * {@inheritDoc}
     *
     * @param ticketRequest Represents the ticket request instance has the data for ticket booking
     * @return The ticket instance
     */
    @Override
    public JsonObject bookTickets(final TicketRequest ticketRequest) {
        final JsonObject jsonObject = validatorFactory.validate(ticketRequest, GetTicketValidator.class);

        if (jsonObject.isEmpty()) {
            final Optional<Ticket> ticket = movieTicketBookingDAO.bookTickets(ticketRequest);

            return ticket.isPresent() ? jsonObject.build(ticket.get()) :
                    jsonObject.put(STATUS, "The seats are already booked");
        }

        return jsonObject;
    }
}