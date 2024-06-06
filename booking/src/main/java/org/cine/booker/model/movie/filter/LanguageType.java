package org.cine.booker.model.movie.filter;

import java.util.Optional;

/**
 * <P>
 * Defines the language type of the movie.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum LanguageType {

    TAMIL(1),
    ENGLISH(2),
    MALAYALAM(3),
    HINDI(4),
    TELUGU(5);

    private final Integer id;

    LanguageType(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the language type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The language type
     */
    public static Optional<LanguageType> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(LanguageType.TAMIL);
            case 2 -> Optional.of(LanguageType.ENGLISH);
            case 3 -> Optional.of(LanguageType.MALAYALAM);
            case 4 -> Optional.of(LanguageType.HINDI);
            case 5 -> Optional.of(LanguageType.TELUGU);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
