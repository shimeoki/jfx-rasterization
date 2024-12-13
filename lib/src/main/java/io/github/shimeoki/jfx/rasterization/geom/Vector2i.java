package io.github.shimeoki.jfx.rasterization.geom;

/**
 * Standard implementation of the {@code Point2i}.
 *
 * @since 2.0.0
 *
 * @see Point2i
 */
public final class Vector2i implements Point2i {

    private int x;
    private int y;

    /**
     * Creates a new instance of {@code Vector2i}.
     *
     * @param x initial value of x (first) coordinate
     * @param y initial value of y (second) coordinate
     *
     * @since 2.0.0
     */
    public Vector2i(final int x, final int y) {
        setX(x);
        setY(y);
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public void setX(final int x) {
        this.x = x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public void setY(final int y) {
        this.y = y;
    }
}
