package org.cine.booker.model.seat;

import org.cine.booker.model.ticket.TicketCategory;

import java.util.Objects;

/**
 * <P>
 * Represents seat entity of the theatre with properties and methods.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Seat {

    private Long id;
    private String name;
    private SeatStatus status;
    private SeatingType seatingType;
    private TicketCategory ticketCategory;
    private Float rate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public SeatingType getSeatingType() {
        return seatingType;
    }

    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    public Float getRate() {
        return rate;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Seat) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class SeatBuilder {

        private final Seat seat;

        public SeatBuilder() {
            seat = new Seat();
        }

        public SeatBuilder setId(final Long id) {
            seat.id = id;
                 
            return this;
        }

        public SeatBuilder setName(final String name) {
            seat.name = name;
                 
            return this;
        }

        public SeatBuilder setStatus(final SeatStatus status) {
            seat.status = status;
                 
            return this;
        }

        public SeatBuilder setSeatingType(final SeatingType seatingType) {
            seat.seatingType = seatingType;
                 
            return this;
        }

        public SeatBuilder setTicketCategory(final TicketCategory ticketCategory) {
            seat.ticketCategory = ticketCategory;
                 
            return this;
        }

        public SeatBuilder setRate(final Float rate) {
            seat.rate = rate;
                 
            return this;
        }

        public Seat build() {
            return seat;
        }
    }
}