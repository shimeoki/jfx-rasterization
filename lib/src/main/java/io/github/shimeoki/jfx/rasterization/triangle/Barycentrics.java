package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.Floats;

/**
 * A class that represents barycentric coordinates using floats.
 *
 * <p>
 * The main usage is to act as a data object for the {@link Filler}.
 * <p>
 * The coordinates are mutable for the usage of the same object instead of
 * creating a new one everytime. It can be seen in
 * {@link Barycentricser}.
 * <p>
 * It is recommended to keep the coordinates normalized (based on the theory).
 * Documentation: <a href=
 * "https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Barycentric_coordinates_on_triangles">Wikipedia</a>.
 *
 * @since 2.0.0
 *
 * @see Filler
 * @see Barycentricser
 */
public final class Barycentrics {

    private float lambda1, lambda2, lambda3;

    /**
     * Creates a new {@link Barycentrics} instance with initial values.
     *
     * @param lambda1 initial value of the first barycentric coordinate
     * @param lambda2 initial value of the second barycentric coordinate
     * @param lambda3 initial value of the third barycentric coordinate
     *
     * @since 2.0.0
     */
    public Barycentrics(
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
     * @since 2.0.0
     */
    public float lambda1() {
        return lambda1;
    }

    /**
     * Sets the value of the first barycentric coordinate.
     *
     * @param v new value of the first barycentric coordinate
     *
     * @since 2.0.0
     */
    public void setLambda1(final float v) {
        lambda1 = v;
    }

    /**
     * Gets the second barycentric coordinate.
     *
     * @return the second barycentric coordinate.
     *
     * @since 2.0.0
     */
    public float lambda2() {
        return lambda2;
    }

    /**
     * Sets the value of the second barycentric coordinate.
     *
     * @param v new value of the second barycentric coordinate
     *
     * @since 2.0.0
     */
    public void setLambda2(final float v) {
        lambda2 = v;
    }

    /**
     * Gets the third barycentric coordinate.
     *
     * @return the third barycentric coordinate.
     *
     * @since 2.0.0
     */
    public float lambda3() {
        return lambda3;
    }

    /**
     * Sets the value of the third barycentric coordinate.
     *
     * @param v new value of the third barycentric coordinate
     *
     * @since 2.0.0
     */
    public void setLambda3(final float v) {
        lambda3 = v;
    }

    /**
     * Gets the boolean value of the statement "are these barycentrics inside of a
     * triangle?".
     *
     * <p>
     * Equals to the boolean value of the statement "are all the barycentric
     * coordinates not negative?".
     * <p>
     * Uses {@link Floats#moreThan(float, float)} internally.
     *
     * @return {@code true} if these barycentric coordinates are inside of the
     *         triangle; {@code false} otherwise
     *
     * @since 2.0.0
     *
     * @see Floats#moreThan(float, float)
     */
    public boolean inside() {
        final boolean ok1 = Floats.moreThan(lambda1, 0f);
        final boolean ok2 = Floats.moreThan(lambda2, 0f);
        final boolean ok3 = Floats.moreThan(lambda3, 0f);

        return ok1 && ok2 && ok3;
    }

    /**
     * Gets the boolean value of the statement "are these barycentrics normalized?".
     *
     * <p>
     * Equals to {@code true} is the sum of all coordinates is 1.
     * <p>
     * Uses {@link Floats#equals(float, float)} internally.
     *
     * @return {@code true} if the sum of these barycentric coordinates is equal to
     *         1; {@code false} otherwise
     *
     * @since 2.0.0
     *
     * @see Floats#equals(float, float)
     */
    public boolean normalized() {
        return Floats.equals(lambda1 + lambda2 + lambda3, 1);
    }
}
