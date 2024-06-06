package org.cine.booker.model.movie.filter;

import java.util.Optional;

/**
 * <P>
 * Defines the format type of the movie.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum FormatType {

    TWO_DIMENSION(1, "2D"),
    THREE_DIMENSION(2, "3D"),
    IMAX(3, "IMAX");

    private final Integer id;
    private final String formatName;

    FormatType(final Integer id, final String formatName) {
        this.id = id;
        this.formatName = formatName;
    }

    /**
     * <p>
     *  Gets the format type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The format type
     */
    public static Optional<FormatType> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(FormatType.TWO_DIMENSION);
            case 2 -> Optional.of(FormatType.THREE_DIMENSION);
            case 3 -> Optional.of(FormatType.IMAX);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }

    public String getFormat() {
        return formatName;
    }
}
