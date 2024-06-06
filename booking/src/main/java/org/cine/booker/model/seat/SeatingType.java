package org.cine.booker.model.seat;

import java.util.Optional;

/**
 * <P>
 * Defines the type of the seating in the theatre.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum SeatingType {

    SOFA(1),
    SWING_BACK(2),
    FIXED_BACK(3);

    private final Integer id;

    SeatingType(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the seat type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The seat type
     */
    public static Optional<SeatingType> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(SeatingType.SOFA);
            case 2 -> Optional.of(SeatingType.SWING_BACK);
            case 3 -> Optional.of(SeatingType.FIXED_BACK);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
