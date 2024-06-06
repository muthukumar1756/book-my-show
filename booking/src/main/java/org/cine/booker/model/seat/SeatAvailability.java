package org.cine.booker.model.seat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>
 * Represents seat availability entity of the theatre with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class SeatAvailability {

    private Integer totalSeatCount;
    private Integer availableSeats;
    private Integer bookedSeats;
    private Collection<Seat> seatList;

    public SeatAvailability() {
        seatList = new ArrayList<>();
    }

    public Integer getTotalSeatCount() {
        return totalSeatCount;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public Integer getBookedSeats() {
        return bookedSeats;
    }

    public Collection<Seat> getSeatList() {
        return Collections.unmodifiableCollection(seatList);
    }

    public void setSeatList(final Collection<Seat> seatList) {
        this.seatList = seatList;
    }

    public static class SeatAvailabilityBuilder {

        private final SeatAvailability seatAvailability;

        public SeatAvailabilityBuilder() {
            seatAvailability = new SeatAvailability();
        }

        public SeatAvailabilityBuilder setTotalSeatCount(final Integer totalSeatCount) {
            seatAvailability.totalSeatCount = totalSeatCount;
                 
            return this;
        }

        public SeatAvailabilityBuilder setAvailableSeats(final Integer availableSeats) {
            seatAvailability.availableSeats = availableSeats;
                 
            return this;
        }

        public SeatAvailabilityBuilder setBookedSeats(final Integer bookedSeats) {
            seatAvailability.bookedSeats = bookedSeats;
                 
            return this;
        }

        public SeatAvailabilityBuilder setSeatList(final Collection<Seat> seatList) {
            seatAvailability.seatList = seatList;
                 
            return this;
        }

        public SeatAvailability build() {
            return seatAvailability;
        }
    }
}
