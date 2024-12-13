package io.github.shimeoki.jfx.rasterization.triangle.geom;

import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
import io.github.shimeoki.jfx.rasterization.triangle.Triangler;

/**
 * An interface that represents a triangle with floats for the coordinates.
 *
 * <p>
 * Triangle can be defined with many ways or methods, but this interface
 * considers only wants the three vertices of the triangle.
 * <p>
 * For the vertices, {@link Pos2f} interface is used. Double precision
 * floating point numbers can be used internally, but it is not recommended.
 * <p>
 * Also, the interface requires {@link #barycentrics(Pos2f)} method implemented.
 * That is needed for the {@link Triangler} to work.
 *
 * @since 1.0.0
 *
 * @see Pos2f
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
    public Pos2f v1();

    /**
     * Gets the second vertex of this triangle.
     *
     * @return the second vertex
     *
     * @since 1.0.0
     */
    public Pos2f v2();

    /**
     * Gets the third vertex of this triangle.
     *
     * @return the third vertex
     *
     * @since 1.0.0
     */
    public Pos2f v3();

    /**
     * Gets the barycentric coordinates of the passed point for this triangle.
     *
     * <p>
     * This method should be consistent with the getters for the vertices. The
     * numbering for the coordinates should align with the vertices numbering.
     * <p>
     * For the conversion formula refer to <a href=
     * "https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates">Wikipedia</a>.
     *
     * @param p the point to get barycentrics for
     *
     * @return barycentric coordinates of the point {@code p} for this triangle
     *
     * @throws NullPointerException if {@code p} is {@code null}
     *
     * @since 1.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentrics barycentrics(final Pos2f p);
}
