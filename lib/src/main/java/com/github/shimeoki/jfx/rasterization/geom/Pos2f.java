package com.github.shimeoki.jfx.rasterization.geom;

/**
 * Interface that represents a static position in 2D space using floats.
 *
 * <p>
 * Because the implementation can be dynamic ({@code x} and {@code y} are not
 * constants), it acts just as a minimal interface for retrieving coordinates,
 * and not guarentees the static behaviour.
 *
 * @author shimeoki
 * @since 1.0.0
 */
public interface Pos2f {

    /**
     * Gets the first coordinate of the position.
     *
     * @return x (first) coordinate
     */
    public float x();

    /**
     * Gets the second coordinate of the position.
     *
     * @return y (second) coordinate
     */
    public float y();
}
