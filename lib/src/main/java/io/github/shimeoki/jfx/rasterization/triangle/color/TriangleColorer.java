package io.github.shimeoki.jfx.rasterization.triangle.color;

import io.github.shimeoki.jfx.rasterization.color.Colorf;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

/**
 * An interface that represents a function to get the colors to fill the
 * triangle.
 *
 * <p>
 * Can be used for filling triangles with one color, gradients or textures.
 * <p>
 * Standard implementations for "monotone" and gradient fill can be found in the
 * library. You can use them as examples for creating your own class/function.
 *
 * @since 1.0.0
 *
 * @see StaticMonotoneTriangleColorer
 * @see DynamicMonotoneTriangleColorer
 * @see StaticGradientTriangleColorer
 * @see DynamicGradientTriangleColorer
 */
@FunctionalInterface
public interface TriangleColorer {

    /**
     * Gets the color for the point on specified barycentrics.
     *
     * @param b the barycentric coordinates
     *
     * @return color for this point
     *
     * @throws NullPointerException if {@code b} is {@code null}
     *
     * @since 1.0.0
     */
    public Colorf get(final TriangleBarycentrics b);
}
