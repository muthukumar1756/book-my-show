package org.cine.booker.model.theatre;

import java.util.Optional;

/**
 * <P>
 * Defines the current status type of the theatre.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum TheatreStatus {

    ACTIVE(1),
    INACTIVE(2);

    private final Integer id;

    TheatreStatus(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the status type of theatre by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The theatre status type
     */
    public static Optional<TheatreStatus> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(TheatreStatus.ACTIVE);
            case 2 -> Optional.of(TheatreStatus.INACTIVE);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
