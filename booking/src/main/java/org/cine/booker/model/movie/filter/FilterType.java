package org.cine.booker.model.movie.filter;

import java.util.Optional;

/**
 * <p>
 * Defines the types of the filters available for movies.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum FilterType {

    LANGUAGE(1),
    FORMAT(2),
    GENRE(3);

    private final Integer id;

    FilterType(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the filter type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The filter type
     */
    public static Optional<FilterType> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(FilterType.LANGUAGE);
            case 2 -> Optional.of(FilterType.FORMAT);
            case 3 -> Optional.of(FilterType.GENRE);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}
