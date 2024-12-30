package io.github.shimeoki.jfx.rasterization;

/**
 * An interface that represents a point in 2D space using integers.
 *
 * @since 2.0.0
 */
public interface Point2i {

    /**
     * Gets the first coordinate of the position.
     *
     * @return x (first) integer coordinate
     *
     * @since 2.0.0
     */
    public int x();

    /**
     * Sets the value of the first coordinate.
     *
     * @param x new value of x (first) coordinate as an integer
     *
     * @since 2.0.0
     */
    public void setX(final int x);

    /**
     * Gets the second coordinate of the position.
     *
     * @return y (second) integer coordinate
     *
     * @since 2.0.0
     */
    public int y();

    /**
     * Sets the value of the second coordinate.
     *
     * @param y new value of y (second) coordinate as an integer
     *
     * @since 2.0.0
     */
    public void setY(final int y);
}
