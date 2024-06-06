package org.cine.booker.model.movie;

import java.util.Optional;

/**
 * <P>
 * Defines the current status type of the movie.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum MovieStatus {

    RUNNING(1),
    RANOUT(2),
    UPCOMING(3);

    private final Integer id;

    MovieStatus(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the status type of movie by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The movie status type
     */
    public static Optional<MovieStatus> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(MovieStatus.RUNNING);
            case 2 -> Optional.of(MovieStatus.RANOUT);
            case 3 -> Optional.of(MovieStatus.UPCOMING);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}