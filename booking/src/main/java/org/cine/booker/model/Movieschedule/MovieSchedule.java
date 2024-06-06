package org.cine.booker.model.Movieschedule;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.cine.booker.model.theatre.Theatre;
import org.cine.common.hibernate.validatorgroup.seat.GetSeatAvailabilityValidator;

/**
 * <p>
 * Represents movie schedule entity of the theatre with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public record MovieSchedule (

    @NotNull(message = "Movie schedule id cant be null", groups = {GetSeatAvailabilityValidator.class})
    @Positive(message = "Movie schedule id cant be negative", groups = {GetSeatAvailabilityValidator.class})
    Long id,
    Theatre theatre) {

    public MovieSchedule(final Long id) {
        this(id, null);
    }
}
