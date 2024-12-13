package io.github.shimeoki.jfx.rasterization.geom;

/**
 * Interface that represents a dynamic point in 2D space using integers.
 *
 * @since 1.0.0
 */
public interface Point2i {

    /**
     * Gets the first coordinate of the position.
     *
     * @return x (first) coordinate
     *
     * @since 1.0.0
     */
    public int x();

    /**
     * Sets the value of the first coordinate.
     *
     * @param x new value of x (first) coordinate
     *
     * @since 1.0.0
     */
    public void setX(final int x);

    /**
     * Gets the second coordinate of the position.
     *
     * @return y (second) coordinate
     *
     * @since 1.0.0
     */
    public int y();

    /**
     * Sets the value of the second coordinate.
     *
     * @param y new value of y (second) coordinate
     *
     * @since 1.0.0
     */
    public void setY(final int y);
}
