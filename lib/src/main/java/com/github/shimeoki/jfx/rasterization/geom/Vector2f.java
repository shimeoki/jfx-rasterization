package com.github.shimeoki.jfx.rasterization.geom;

/**
 * Standard implementation of the {@code Point2f}.
 *
 * <p>
 * In the library it both acts as static and dynamic implementation where
 * needed.
 *
 * @since 1.0.0
 *
 * @see Point2f
 */
public final class Vector2f implements Point2f {

    private float x;
    private float y;

    /**
     * Creates a new instance of {@code Vector2f}.
     *
     * @param x initial value of x (first) coordinate
     * @param y initial value of y (second) coordinate
     *
     * @since 1.0.0
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
    public void addX(final float dx) {
        setX(x + dx);
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public void setY(final float y) {
        this.y = y;
    }

    @Override
    public void addY(final float dy) {
        setY(y + dy);
    }
}
