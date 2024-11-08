package com.github.shimeoki.jfx.rasterization.color;

import javafx.scene.paint.Color;

/**
 * Interface that represents a static (non-modifiable) color using floats.
 *
 * <p>
 * It is made to simplify calculations, because {@code double} precision is
 * mostly unneeded.
 * <p>
 * Color values range from 0 to 1 inclusively.
 * <p>
 * This interface supports alpha.
 * <p>
 * All implementations of this interface should be able to convert the object to
 * the JavaFX's {@link javafx.scene.paint.Color Color} class.
 *
 * @author shimeoki
 * @since 1.0.0
 *
 * @see javafx.scene.paint.Color
 */
public interface Colorf {

    /**
     * Gets this color's red channel.
     *
     * @return red color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float red();

    /**
     * Gets this color's green channel.
     *
     * @return green color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float green();

    /**
     * Gets this color's blue channel.
     *
     * @return blue color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float blue();

    /**
     * Gets this color's alpha channel.
     *
     * <p>
     * Should be equivalent to:
     *
     * <pre>{@code
     * 1 - transparency()
     * }</pre>
     *
     * @return alpha color channel float value in range from 0.0 to 1.0
     *
     * @see #transparency()
     *
     * @since 1.0.0
     */
    public float alpha();

    /**
     * Gets this color's transparency channel.
     *
     * <p>
     * Should be equivalent to:
     *
     * <pre>{@code
     * 1 - alpha()
     * }</pre>
     *
     * @return transparency color channel float value in range from 0.0 to 1.0
     *
     * @see #alpha()
     *
     * @since 1.0.0
     */
    public float transparency();

    /**
     * Gets a {@link javafx.scene.paint.Color Color} representing this color.
     *
     * @return JavaFX's {@code Color} class with equivalent color channels
     *
     * @see javafx.scene.paint.Color
     */
    public Color jfxColor();
}
