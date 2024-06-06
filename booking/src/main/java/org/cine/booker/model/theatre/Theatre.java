package org.cine.booker.model.theatre;

import java.util.Objects;

import org.cine.booker.model.screen.Screen;

/**
 * <p>
 * Represents theatre entity with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Theatre {

    private Long id;
    private String name;
    private Screen screen;
    private String location;
    private TheatreStatus status;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public TheatreStatus getStatus() {
        return status;
    }

    public Screen getScreen() {
        return screen;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Theatre) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class TheatreBuilder {

        private final Theatre theatre;

        public TheatreBuilder() {
            theatre = new Theatre();
        }

        public TheatreBuilder setId(final Long id) {
            theatre.id = id;
                 
            return this;
        }

        public TheatreBuilder setName(final String name) {
            theatre.name = name;

            return this;
        }

        public TheatreBuilder setScreen(final Screen screen) {
            theatre.screen = screen;

            return this;
        }

        public TheatreBuilder setLocation(final String location) {
            theatre.location = location;

            return this;
        }

        public TheatreBuilder setStatus(final TheatreStatus status) {
            theatre.status = status;
                 
            return this;
        }

        public Theatre build() {
            return theatre;
        }
    }
}