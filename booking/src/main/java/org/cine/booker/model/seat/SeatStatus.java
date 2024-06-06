package org.cine.booker.model.seat;

import java.util.Optional;

/**
 * <P>
 * Defines the status type of the seat.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum SeatStatus {

    AVAILABLE(1),
    BOOKED(2);

    private final Integer id;

    SeatStatus(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the status type of seat by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The seat status type
     */
    public static Optional<SeatStatus> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(SeatStatus.AVAILABLE);
            case 2 -> Optional.of(SeatStatus.BOOKED);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
