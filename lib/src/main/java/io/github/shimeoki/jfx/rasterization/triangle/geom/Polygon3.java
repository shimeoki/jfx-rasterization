package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Point2f;

/**
 * A default {@link Triangle} implementation.
 * 
 * <p>
 * Does store the references to the original points on creation. Getters of the
 * vertices return the same reference everytime.
 *
 * @since 2.0.0
 *
 * @see Triangle
 */
public final class Polygon3 implements Triangle {

    private final Point2f v1, v2, v3;

    /**
     * Creates a new {@code Polygon3} instance.
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 2.0.0
     */
    public Polygon3(final Point2f v1, final Point2f v2, final Point2f v3) {
        this.v1 = Objects.requireNonNull(v1);
        this.v2 = Objects.requireNonNull(v2);
        this.v3 = Objects.requireNonNull(v3);
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
