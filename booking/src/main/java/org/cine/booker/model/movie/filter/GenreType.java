package org.cine.booker.model.movie.filter;

import java.util.Optional;

/**
 * <P>
 * Defines the genre type of the movie.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum GenreType {

    ADVENTURE(1),
    DRAMA(2),
    ROMANCE(3),
    SCI_FI(4),
    HORROR(5),
    THRILLER(6),
    ACTION(7),
    FANTASY(8);

    private final Integer id;

    GenreType(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the genre type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The genre type
     */
    public static Optional<GenreType> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(GenreType.ADVENTURE);
            case 2 -> Optional.of(GenreType.DRAMA);
            case 3 -> Optional.of(GenreType.ROMANCE);
            case 4 -> Optional.of(GenreType.SCI_FI);
            case 5 -> Optional.of(GenreType.HORROR);
            case 6 -> Optional.of(GenreType.THRILLER);
            case 7 -> Optional.of(GenreType.ACTION);
            case 8 -> Optional.of(GenreType.FANTASY);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
