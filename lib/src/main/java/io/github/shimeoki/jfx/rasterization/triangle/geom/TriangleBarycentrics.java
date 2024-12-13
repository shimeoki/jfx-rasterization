package io.github.shimeoki.jfx.rasterization.triangle.geom;

import io.github.shimeoki.jfx.rasterization.math.Floats;

/**
 * A class that represents barycentric coordinates using floats.
 *
 * <p>
 * It is recommended to keep the coordinates normalized (based on the theory).
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
 */
public final class TriangleBarycentrics {

    private float lambda1;
    private float lambda2;
    private float lambda3;

    public TriangleBarycentrics(
            final float lambda1, final float lambda2, final float lambda3) {

        setLambda1(lambda1);
        setLambda2(lambda2);
        setLambda3(lambda3);
    }

    /**
     * Gets the first barycentric coordinate.
     *
     * @return the first barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda1() {
        return lambda1;
    }

    public void setLambda1(final float v) {
        lambda1 = v;
    }

    /**
     * Gets the second barycentric coordinate.
     *
     * @return the second barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda2() {
        return lambda2;
    }

    public void setLambda2(final float v) {
        lambda2 = v;
    }

    /**
     * Gets the third barycentric coordinate.
     *
     * @return the third barycentric coordinate.
     *
     * @since 1.0.0
     */
    public float lambda3() {
        return lambda3;
    }

    public void setLambda3(final float v) {
        lambda3 = v;
    }

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
    public boolean inside() {
        final boolean ok1 = Floats.moreThan(lambda1, 0f);
        final boolean ok2 = Floats.moreThan(lambda2, 0f);
        final boolean ok3 = Floats.moreThan(lambda3, 0f);

        return ok1 && ok2 && ok3;
    }

    public boolean normalized() {
        return Floats.equals(lambda1 + lambda2 + lambda3, 1);
    }
}
