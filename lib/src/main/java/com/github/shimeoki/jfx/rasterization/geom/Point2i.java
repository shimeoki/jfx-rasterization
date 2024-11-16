package com.github.shimeoki.jfx.rasterization.geom;

/**
 * Interface that represents a dynamic point in 2D space using integers.
 *
 * <p>
 * It extends {@code Pos2i}, so it has methods for getting the coordinates from
 * this interface.
 *
 * @since 1.0.0
 *
 * @see Pos2i
 */
public interface Point2i extends Pos2i {

    /**
     * Sets the value of the first coordinate.
     *
     * @param x new value of x (first) coordinate
     *
     * @since 1.0.0
     */
    public void setX(final int x);

    /**
     * Sets the value of the second coordinate.
     *
     * @param y new value of y (second) coordinate
     *
     * @since 1.0.0
     */
    public void setY(final int y);

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
    public void addX(final int dx);

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
    public void addY(final int dy);
}
