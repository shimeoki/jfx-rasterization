package io.github.shimeoki.jfx.rasterization.triangle.geom;

/**
 * An interface that represents barycentric coordinates using floats.
 *
 * <p>
 * It is recommended to keep the coordinates normalized (based on the theory),
 * but the interface cannot restrict that. Because of that, the standard
 * implementation ({@link NormalizedTriangleBarycentrics}) is named explicitly.
 * <p>
 * Requires the {@link #inside()} method implementation. It is not a static
 * method (based on the theory, barycentrics are inside, if all 3 coordinates
 * are equal to or more than 0) to give more freedom for the implementation,
 * because in some cases (anti-aliasing?) it can be useful.
 * <p>
 * Documentation: <a href=
 * "https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Barycentric_coordinates_on_triangles">Wikipedia</a>.
 *
 * @since 1.0.0
 *
 * @see NormalizedTriangleBarycentrics
 */
public interface TriangleBarycentrics {

    /**
     * Gets the first barycentric coordinate.
     *
     * @return the first barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda1();

    /**
     * Gets the second barycentric coordinate.
     *
     * @return the second barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda2();

    /**
     * Gets the third barycentric coordinate.
     *
     * @return the third barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda3();

    /**
     * Gets the boolean value of the statement "are these barycentrics inside of the
     * triangle?".
     *
     * <p>
     * By default, it should equal to the boolean value of the statement "are all
     * the barycentric coordinates are not negative?".
     *
     * @return {@code true} if these barycentric coordinates are inside of the
     *         triangle; {@code false} otherwise
     *
     * @since 1.0.0
     */
    public boolean inside();
}
