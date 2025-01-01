package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.Colorf;

/**
 * An interface that represents a function to get the colors to fill the
 * triangle.
 *
 * <p>
 * Can be used for filling triangles with one color, gradients or textures.
 * <p>
 * Standard implementations for monotone and gradient fill can be found in the
 * library. You can use them as examples for creating your own class/function.
 *
 * @since 2.0.0
 *
 * @see SolidFiller
 * @see GradientFiller
 */
@FunctionalInterface
public interface Filler {

    /**
     * Gets the color for the point on specified barycentrics.
     *
     * @param b the barycentric coordinates
     *
     * @return color for this point; can be {@code null}
     *
     * @throws NullPointerException if {@code b} is {@code null}
     *
     * @since 2.0.0
     */
    public Colorf color(final Barycentrics b);
}
