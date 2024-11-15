package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.geom.Point2f;

/**
 * An interface that represents a triangle with floats for the coordinates.
 *
 * <p>
 * Triangle can be defined with many ways or methods, but this interface
 * considers only wants the three vertices of the triangle.
 * <p>
 * For the vertices, {@link Point2f} interface is used. Double precision
 * floating point numbers can be used internally, but it is not recommended.
 * <p>
 * Also, the interface requires {@link #barycentrics(Pos2f)} method implemented.
 * That is needed for the {@link Triangler} to work.
 *
 * @author shimeoki
 * @since 1.0.0
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
     * @since 1.0.0
     */
    public Point2f v1();

    /**
     * Gets the second vertex of this triangle.
     *
     * @return the second vertex
     *
     * @since 1.0.0
     */
    public Point2f v2();

    /**
     * Gets the third vertex of this triangle.
     *
     * @return the third vertex
     *
     * @since 1.0.0
     */
    public Point2f v3();

    /**
     * Gets the barycentric coordinates of the point for this triangle.
     *
     * <p>
     * This method should be consistent with the getters for the vertices. The
     * numbering for the coordinates should align with the vertices numbering.
     *
     * @param p the point to get barycentrics for
     *
     * @return barycentric coordinates of the point {@code p} for this triangle
     *
     * @since 1.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentrics barycentrics(final Pos2f p);
}
