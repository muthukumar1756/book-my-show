package org.cine.booker.model.ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.cine.booker.model.theatre.Theatre;

/**
 * <p>
 * Represents ticket entity with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Ticket {

    private Long id;
    private Theatre theatre;
    private Collection<String> seatList;
    private Integer seatCount;
    private Float amountPaid;

    public Ticket() {
        seatList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(final Theatre theatre) {
        this.theatre = theatre;
    }

    public Collection<String> getSeatList() {
        return Collections.unmodifiableCollection(seatList);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Ticket) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class TicketBuilder {

        private final Ticket ticket;

        public TicketBuilder() {
            ticket = new Ticket();
        }

        public TicketBuilder setId(final Long id) {
            ticket.id = id;
                 
            return this;
        }

        public TicketBuilder setSeatCount(final Integer seatCount) {
            ticket.seatCount = seatCount;
                 
            return this;
        }

        public TicketBuilder setAmountPaid(final Float amountPaid) {
            ticket.amountPaid = amountPaid;
                 
            return this;
        }

        public TicketBuilder setTheatre(final Theatre theatre) {
            ticket.theatre = theatre;
                 
            return this;
        }

        public TicketBuilder setSeatList(final Collection<String> seatList) {
            ticket.seatList = seatList;
                 
            return this;
        }

        public Ticket build() {
            return ticket;
        }
    }
}