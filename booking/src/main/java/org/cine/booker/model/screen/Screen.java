package org.cine.booker.model.screen;

import java.util.Objects;

import org.cine.booker.model.movie.Movie;

/**
 * <p>
 * Represents screen entity of the theatre with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Screen {

    private Long id;
    private String name;
    private Movie movie;
    private String showTime;
    private String showDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getShowDate() {
        return showDate;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Screen) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class ScreenBuilder {

        private final Screen screen;

        public ScreenBuilder() {
            screen = new Screen();
        }

        public ScreenBuilder setId(final Long id) {
            screen.id = id;
                 
            return this;
        }

        public ScreenBuilder setName(final String name) {
            screen.name = name;
                 
            return this;
        }

        public ScreenBuilder setMovie(final Movie movie) {
            screen.movie = movie;
                 
            return this;
        }

        public ScreenBuilder setShowTime(final String showTime) {
            screen.showTime = showTime;
                 
            return this;
        }

        public ScreenBuilder setShowDate(final String showDate) {
            screen.showDate = showDate;
                 
            return this;
        }

        public Screen build() {
            return screen;
        }
    }
}