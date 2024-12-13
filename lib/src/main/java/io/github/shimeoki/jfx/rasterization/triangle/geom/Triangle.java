package io.github.shimeoki.jfx.rasterization.triangle.geom;

import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.triangle.Triangler;

/**
 * An interface that represents a triangle with floats for the coordinates.
 *
 * <p>
 * Triangles can be defined with many ways or methods, but this interface
 * only wants the three vertices of the triangle.
 * <p>
 * For the vertices {@link Point2f} interface is used. Double precision
 * floating point numbers can be used internally, but it is not recommended.
 * <p>
 * The main usage of this interface is to act as a data object for the
 * {@link Triangler}.
 *
 * @since 2.0.0
 *
 * @see Point2f
 * @see Triangler
 */
public interface Triangle {

    /**
     * Gets the first vertex of this triangle.
     *
     * @return the first vertex
     *
     * @since 2.0.0
     */
    public Point2f v1();

    /**
     * Gets the second vertex of this triangle.
     *
     * @return the second vertex
     *
     * @since 2.0.0
     */
    public Point2f v2();

    /**
     * Gets the third vertex of this triangle.
     *
     * @return the third vertex
     *
     * @since 2.0.0
     */
    public Point2f v3();
}
