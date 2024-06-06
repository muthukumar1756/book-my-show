package org.cine.booker.model.ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.Collection;

import org.cine.common.hibernate.validatorgroup.ticket.GetTicketValidator;

/**
 * <p>
 * Represents ticket request entity with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class TicketRequest {

    @NotNull(message = "User id cant be null", groups = {GetTicketValidator.class})
    @Positive(message = "User cant be negative", groups = {GetTicketValidator.class})
    private Long userId;
    @NotNull(message = "Show schedule id cant be null", groups = {GetTicketValidator.class})
    @Positive(message = "Show schedule cant be negative", groups = {GetTicketValidator.class})
    private Long movieScheduleId;
    @NotNull(message = "Seat list cant be null", groups = {GetTicketValidator.class})
    private Collection<Long> seatIdList;
    private Collection<String> seatNameList;
    private Integer seatCount;
    private Float amountPaid;

    public TicketRequest() {
        seatIdList = new ArrayList<>();
        seatNameList = new ArrayList<>();
    }

    public Long getMovieScheduleId() {
        return movieScheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public Collection<Long> getSeatIdList() {
        return seatIdList;
    }

    public Collection<String> getSeatNameList() {
        return seatNameList;
    }

    public static class TicketRequestBuilder {

        private final TicketRequest ticketRequest;

        public TicketRequestBuilder() {
            ticketRequest = new TicketRequest();
        }

        public TicketRequestBuilder setUserId(final Long userId) {
            ticketRequest.userId = userId;
                 
            return this;
        }

        public TicketRequestBuilder setMovieScheduleId(final Long movieScheduleId) {
            ticketRequest.movieScheduleId = movieScheduleId;
                 
            return this;
        }

        public TicketRequestBuilder setSeatList(final Collection<Long> seatList) {
            ticketRequest.seatIdList = seatList;
                 
            return this;
        }

        public TicketRequestBuilder setSeatNameList(final Collection<String> seatNameList) {
            ticketRequest.seatNameList = seatNameList;
                 
            return this;
        }

        public TicketRequestBuilder setSeatCount(final Integer seatCount) {
            ticketRequest.seatCount = seatCount;
                 
            return this;
        }

        public TicketRequestBuilder setAmountPaid(final Float amountPaid) {
            ticketRequest.amountPaid = amountPaid;
                 
            return this;
        }

        public TicketRequest build() {
            return ticketRequest;
        }
    }
}