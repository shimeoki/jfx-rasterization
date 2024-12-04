package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.math.Floats;

/**
 * One of the default {@link Triangle} implementations, "moving" the vertices on
 * change.
 *
 * <p>
 * It's called "dynamic", because the triangle will change, if the
 * original points, passed to the constructor will change.
 * <p>
 * Does store the references to the original points on creation. Getters of the
 * vertices return the same reference everytime.
 * <p>
 * Because of that, {@link #barycentrics(Pos2f)} calculates the coordinates from
 * scratch on each call, because it doesn't remember the previous coordinates.
 * <p>
 * If use case does suppose that triangles will change, it's advised to use
 * this class, instead of creating a new triangle everytime.
 * <p>
 * If you need an opposite behaviour (triangle is "frozen" in place), take a
 * look at the {@link StaticTriangle}.
 *
 * @since 1.0.0
 *
 * @see Triangle
 * @see StaticTriangle
 */
public final class DynamicTriangle implements Triangle {

    private final Point2f v1;
    private final Point2f v2;
    private final Point2f v3;

    /**
     * Creates a new {@code DynamicTriangle} instance.
     *
     * <p>
     * All principles of the internal usage are described in the javadoc of the
     * class. It's advised to read it before use.
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 1.0.0
     */
    public DynamicTriangle(
            final Point2f v1,
            final Point2f v2,
            final Point2f v3) {

        Objects.requireNonNull(v1);
        Objects.requireNonNull(v2);
        Objects.requireNonNull(v3);

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    @Override
    public Point2f v1() {
        return v1;
    }

    @Override
    public Point2f v2() {
        return v2;
    }

    @Override
    public Point2f v3() {
        return v3;
    }

    @Override
    public TriangleBarycentrics barycentrics(final Pos2f p) {
        Objects.requireNonNull(p);

        final float x = p.x();
        final float y = p.y();

        final float x1 = v1.x();
        final float y1 = v1.y();

        final float x2 = v2.x();
        final float y2 = v2.y();

        final float x3 = v3.x();
        final float y3 = v3.y();

        final float dx13 = x1 - x3;
        final float dy13 = y1 - y3;

        final float dx23 = x2 - x3;
        final float dy23 = y2 - y3;

        final float dx3 = x - x3;
        final float dy3 = y - y3;

        final float numerator1 = dy23 * dx3 - dx23 * dy3;
        final float numerator2 = -dy13 * dx3 + dx13 * dy3;
        final float numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        final float denominator = dy23 * dx13 - dx23 * dy13;
        if (Floats.equals(denominator, 0)) {
            return new NormalizedTriangleBarycentrics(1, 0, 0);
        }

        final float d = 1f / denominator;

        final float lambda1 = numerator1 * d;
        final float lambda2 = numerator2 * d;
        final float lambda3 = numerator3 * d;

        return new NormalizedTriangleBarycentrics(lambda1, lambda2, lambda3);
    }
}
