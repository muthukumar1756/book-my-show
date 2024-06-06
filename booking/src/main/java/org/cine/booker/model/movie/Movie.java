package org.cine.booker.model.movie;

import java.util.Objects;

/**
 * <P>
 * Represents movie entity with properties and methods.
 * </P>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class Movie {

    private Long id;
    private String name;
    private String description;
    private Float rating;
    private Long votes;
    private String language;
    private MovieStatus status;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getRating() {
        return rating;
    }

    public Long getVotes() {
        return votes;
    }

    public String getLanguage() {
        return language;
    }

    public MovieStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Movie) {
            return this.hashCode() == object.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class MovieBuilder {

        private final Movie movie;

        public MovieBuilder() {
            movie = new Movie();
        }

        public MovieBuilder setId(final Long id) {
            movie.id = id;
                 
            return this;
        }

        public MovieBuilder setName(final String name) {
            movie.name = name;
                 
            return this;
        }

        public MovieBuilder setDescription(final String description) {
            movie.description = description;
                 
            return this;
        }

        public MovieBuilder setRating(final Float rating) {
            movie.rating = rating;
                 
            return this;
        }

        public MovieBuilder setVotes(final Long votes) {
            movie.votes = votes;
                 
            return this;
        }

        public MovieBuilder setLanguage(final String language) {
            movie.language = language;
                 
            return this;
        }

        public MovieBuilder setStatus(final MovieStatus status) {
            movie.status = status;
                 
            return this;
        }

        public Movie build() {
            return movie;
        }
    }
}