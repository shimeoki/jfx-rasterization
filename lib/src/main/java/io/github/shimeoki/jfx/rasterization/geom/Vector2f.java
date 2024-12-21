package io.github.shimeoki.jfx.rasterization.geom;

/**
 * Standard implementation of the {@link Point2f}.
 *
 * @since 2.0.0
 *
 * @see Point2f
 */
public final class Vector2f implements Point2f {

    private float x, y;

    /**
     * Creates a new instance of {@code Vector2f}.
     *
     * @param x initial value of x (first) coordinate
     * @param y initial value of y (second) coordinate
     *
     * @since 2.0.0
     */
    public Vector2f(final float x, final float y) {
        setX(x);
        setY(y);
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public void setX(final float x) {
        this.x = x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public void setY(final float y) {
        this.y = y;
    }
}
