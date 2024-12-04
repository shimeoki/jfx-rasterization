package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
import io.github.shimeoki.jfx.rasterization.geom.Vector2f;
import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.math.Floats;

/**
 * One of the default {@link Triangle} implementations, "freezing" the vertices
 * in place.
 *
 * <p>
 * It's called "static", because the triangle will stay the same, even if the
 * original points, passed to the constructor will change.
 * <p>
 * Doesn't store the references to the original points on creation. It
 * converts the values to the {@code final} primitives and caches everything it
 * can.
 * <p>
 * Because of that, getters for the vertices return a new instance everytime.
 * <p>
 * If use case doesn't suppose that triangles will change, it's advised to use
 * this class, because it's slightly more performant and more explicit.
 * <p>
 * If you need an opposite behaviour (triangle keeps the references and changes
 * respectfully), take a look at the {@link DynamicTriangle}.
 *
 * @since 1.0.0
 *
 * @see Triangle
 * @see DynamicTriangle
 */
public final class StaticTriangle implements Triangle {

    private final float x1;
    private final float y1;

    private final float x2;
    private final float y2;

    private final float x3;
    private final float y3;

    private final float dx13;
    private final float dy13;

    private final float dx23;
    private final float dy23;

    private final float d;

    /**
     * Creates a new {@code StaticTriangle} instance.
     *
     * <p>
     * All principles of the internal usage are described in the javadoc of the
     * class. It's advised to read it before use.
     *
     * @param p1 position of the first vertex
     * @param p2 position of the second vertex
     * @param p3 position of the third vertex
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 1.0.0
     */
    public StaticTriangle(
            final Pos2f p1,
            final Pos2f p2,
            final Pos2f p3) {

        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        x1 = p1.x();
        y1 = p1.y();

        x2 = p2.x();
        y2 = p2.y();

        x3 = p3.x();
        y3 = p3.y();

        dx13 = x1 - x3;
        dy13 = y1 - y3;

        dx23 = x2 - x3;
        dy23 = y2 - y3;

        final float denominator = dy23 * dx13 - dx23 * dy13;
        if (Floats.equals(denominator, 0)) {
            d = 0;
        } else {
            d = 1 / denominator;
        }
    }

    @Override
    public Point2f v1() {
        return new Vector2f(x1, y1);
    }

    @Override
    public Point2f v2() {
        return new Vector2f(x2, y2);
    }

    @Override
    public Point2f v3() {
        return new Vector2f(x3, y3);
    }

    @Override
    public TriangleBarycentrics barycentrics(final Pos2f p) {
        Objects.requireNonNull(p);

        if (d == 0) {
            return new NormalizedTriangleBarycentrics(1, 0, 0);
        }

        final float x = p.x();
        final float y = p.y();

        final float dx3 = x - x3;
        final float dy3 = y - y3;

        final float numerator1 = dy23 * dx3 - dx23 * dy3;
        final float numerator2 = -dy13 * dx3 + dx13 * dy3;
        final float numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        final float lambda1 = numerator1 * d;
        final float lambda2 = numerator2 * d;
        final float lambda3 = numerator3 * d;

        return new NormalizedTriangleBarycentrics(lambda1, lambda2, lambda3);
    }
}
