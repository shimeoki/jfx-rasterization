package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Point2f;

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
}
