package io.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.Colorf;

/**
 * One of the standard implementations of {@link Filler} to fill the
 * triangles with one color.
 * 
 * <p>
 * Accepts a {@link Colorf} on creation and fills the triangles with the color
 * if barycentrics object is not null.
 * 
 * @since 2.0.0
 *
 * @see Filler
 * @see Colorf
 */
public final class SolidFiller implements Filler {

    private final Colorf color;

    /**
     * Creates a new {@link SolidFiller} instance.
     *
     * @param c the color to use
     *
     * @throws NullPointerException if {@code c} is {@code null}
     *
     * @since 2.0.0
     */
    public SolidFiller(final Colorf c) {
        color = Objects.requireNonNull(c);
    }

    @Override
    public Colorf color(final Barycentrics b) {
        Objects.requireNonNull(b);

        return color;
    }
}
