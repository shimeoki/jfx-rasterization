package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.Colorf;
import io.github.shimeoki.jfx.rasterization.Point2i;

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
     * Gets the color for the point on specified barycentrics and/or pixel
     * coordinates.
     *
     * <p>
     * The parameters can be not copies, but references to the objects, used by the
     * triangler.
     *
     * @param b barycentric coordinates
     * @param p pixel coordinates
     *
     * @return color for this point; can be {@code null}
     *
     * @throws NullPointerException if {@code b} or {@code p} is {@code null}
     *
     * @since 2.0.0
     */
    public Colorf color(final Barycentrics b, final Point2i p);
}
