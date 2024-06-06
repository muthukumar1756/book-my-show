package org.cine.booker.model.movie.filter;

import java.util.Collection;

/**
 * <p>
 * Represents movie filter entity with properties and methods.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public record MovieFilter(Collection<Integer> languageIds, Collection<Integer> formatIds, Collection<Integer> genreIds) {
}