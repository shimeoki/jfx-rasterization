package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.math.Floats;

/**
 * A class for memory and time efficient {@link TriangleBarycentrics}
 * generation from a {@link Triangle}.
 *
 * <p>
 * Exists for 3 reasons: to not allocate new barycentrics each time, to cache
 * the calculations for a single triangle and to not enforce the implementation
 * of this functionality on user.
 *
 * @since 2.0.0
 *
 * @see Triangle
 * @see TriangleBarycentrics
 */
public final class TriangleBarycentricser {

    private Triangle triangle;
    private final TriangleBarycentrics barycentrics;

    private Point2f v1, v2, v3;

    private float x, y, x1, y1, x2, y2, x3, y3;
    private float dx3, dy3, dx13, dy13, dx23, dy23;

    private float numerator1, numerator2, numerator3;
    private float k; // inverted denominator

    /**
     * Creates a new {@link TriangleBarycentricser} instance.
     *
     * <p>
     * Creates a new {@link TriangleBarycentrics} with zeros as coordinates.
     *
     * @since 2.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentricser() {
        barycentrics = new TriangleBarycentrics(0, 0, 0);
    }

    /**
     * Returns a reference to the triangle barycentrics in this barycentricser
     * object.
     *
     * @return reference to the barycentrics in this barycentricser
     *
     * @since 2.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentrics barycentrics() {
        return barycentrics;
    }

    /**
     * Returns a reference to the current used triangle in this barycentricser
     * object.
     *
     * @return reference to the triangle in this barycentricser
     *
     * @since 2.0.0
     *
     * @see Triangle
     */
    public Triangle triangle() {
        return triangle;
    }

    /**
     * Sets the new triangle to calculate the barycentrics for.
     *
     * <p>
     * Updates the barycentrics in the object automatically on call.
     *
     * @param t the triangle to use
     *
     * @throws NullPointerException if {@code t} is {@code null}
     *
     * @since 2.0.0
     *
     * @see Triangle
     */
    public void setTriangle(final Triangle t) {
        triangle = Objects.requireNonNull(t);
        update();
    }

    /**
     * Updates cached points to the current state of the used triangle.
     *
     * <p>
     * Because the triangle can be modified externally (and just a new one can be
     * set), but the rasterization process considers only a single frame, the update
     * is a separate method.
     *
     * @since 2.0.0
     */
    public void update() {
        v1 = triangle.v1();
        v2 = triangle.v2();
        v3 = triangle.v3();

        x1 = v1.x();
        y1 = v1.y();

        x2 = v2.x();
        y2 = v2.y();

        x3 = v3.x();
        y3 = v3.y();

        dx13 = x1 - x3;
        dy13 = y1 - y3;

        dx23 = x2 - x3;
        dy23 = y2 - y3;

        k = dy23 * dx13 - dx23 * dy13; // denominator
        if (Floats.equals(k, 0)) {
            k = 0;
        } else {
            k = 1f / k;
        }
    }

    /**
     * Calculates the barycentrics in this barycenctricser based on the current
     * point data.
     *
     * <p>
     * Updates the triangle barycentrics object accordingly on call.
     *
     * @param p a point to calculate barycentrics for
     *
     * @throws NullPointerException if {@code p} is {@code null}
     *
     * @since 2.0.0
     *
     * @see Point2f
     */
    public void calculate(final Point2f p) {
        Objects.requireNonNull(p);

        x = p.x();
        y = p.y();

        dx3 = x - x3;
        dy3 = y - y3;

        numerator1 = dy23 * dx3 - dx23 * dy3;
        numerator2 = -dy13 * dx3 + dx13 * dy3;
        numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        barycentrics.setLambda1(numerator1 * k);
        barycentrics.setLambda2(numerator2 * k);
        barycentrics.setLambda3(numerator3 * k);
    }
}
