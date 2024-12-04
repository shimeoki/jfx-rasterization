package io.github.shimeoki.jfx.rasterization.geom;

/**
 * Interface that represents a dynamic point in 2D space using floats.
 *
 * <p>
 * It extends {@code Pos2f}, so it has methods for getting the coordinates from
 * this interface.
 *
 * @since 1.0.0
 *
 * @see Pos2f
 */
public interface Point2f extends Pos2f {

    /**
     * Sets the value of the first coordinate.
     *
     * @param x new value of x (first) coordinate
     *
     * @since 1.0.0
     */
    public void setX(final float x);

    /**
     * Sets the value of the second coordinate.
     *
     * @param y new value of y (second) coordinate
     *
     * @since 1.0.0
     */
    public void setY(final float y);

    /**
     * Adds {@code dx} to the first coordinate.
     *
     * <p>
     * Should be equivalent to:
     *
     * <pre>{@code
     * p.setX(p.x() + dx)
     * }</pre>
     *
     * @param dx value to add to the current x (first) coordinate
     *
     * @since 1.0.0
     */
    public void addX(final float dx);

    /**
     * Adds {@code dy} to the second coordinate.
     *
     * <p>
     * Should be equivalent to:
     *
     * <pre>{@code
     * p.setY(p.y() + dy)
     * }</pre>
     *
     * @param dy value to add to the current y (second) coordinate
     *
     * @since 1.0.0
     */
    public void addY(final float dy);
}
