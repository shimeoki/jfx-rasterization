package io.github.shimeoki.jfx.rasterization.geom;

/**
 * Interface that represents a dynamic point in 2D space using floats.
 *
 * @since 1.0.0
 */
public interface Point2f {

    /**
     * Gets the first coordinate of the position.
     *
     * @return x (first) coordinate
     *
     * @since 1.0.0
     */
    public float x();

    /**
     * Sets the value of the first coordinate.
     *
     * @param x new value of x (first) coordinate
     *
     * @since 1.0.0
     */
    public void setX(final float x);

    /**
     * Gets the second coordinate of the position.
     *
     * @return y (second) coordinate
     *
     * @since 1.0.0
     */
    public float y();

    /**
     * Sets the value of the second coordinate.
     *
     * @param y new value of y (second) coordinate
     *
     * @since 1.0.0
     */
    public void setY(final float y);
}
