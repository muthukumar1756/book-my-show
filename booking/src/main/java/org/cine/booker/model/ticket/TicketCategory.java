package org.cine.booker.model.ticket;

import java.util.Optional;

/**
 * <P>
 * Defines the categories ticket.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public enum TicketCategory {

    ELITE(1),
    GOLD(2),
    BUDGET(3);

    private final Integer id;

    TicketCategory(final Integer id) {
        this.id = id;
    }

    /**
     * <p>
     *  Gets the ticket category type by using id
     * </p>
     *
     * @param id Represents the id of the enum type
     * @return The ticket category type
     */
    public static Optional<TicketCategory> getTypeById(final Integer id) {
        return switch (id) {
            case 1 -> Optional.of(TicketCategory.ELITE);
            case 2 -> Optional.of(TicketCategory.GOLD);
            case 3 -> Optional.of(TicketCategory.BUDGET);
            default -> Optional.empty();
        };
    }

    public Integer getId() {
        return id;
    }
}