package io.github.shimeoki.jfx.rasterization;

import io.github.shimeoki.jfx.rasterization.math.Floats;

import javafx.scene.paint.Color;

/**
 * Simple color class with float values.
 *
 * <p>
 * Uses 4 (RGBA) color channels as floats to store the color.
 *
 * @since 2.0.0
 *
 * @see HTMLColorf
 */
public final class Colorf {

    private final float red, green, blue, alpha;

    /**
     * Creates a new {@code Colorf} instance.
     *
     * <p>
     * Uses {@link Floats#confined(float, float, float) Floats.confined(0, v, 1)}
     * to write the values.
     *
     * @param r red color channel float value
     * @param g green color channel float value
     * @param b blue color channel float value
     * @param a alpha color channel float value
     *
     * @since 2.0.0
     *
     * @see Floats#confined(float, float, float)
     */
    public Colorf(
            final float r, final float g, final float b, final float a) {

        red = Floats.confined(0, r, 1);
        green = Floats.confined(0, g, 1);
        blue = Floats.confined(0, b, 1);
        alpha = Floats.confined(0, a, 1);
    }

    /**
     * Gets this color's red channel.
     *
     * @return red color channel float value in range from 0.0 to 1.0
     *
     * @since 2.0.0
     */
    public float red() {
        return red;
    }

    /**
     * Gets this color's green channel.
     *
     * @return green color channel float value in range from 0.0 to 1.0
     *
     * @since 2.0.0
     */
    public float green() {
        return green;
    }

    /**
     * Gets this color's blue channel.
     *
     * @return blue color channel float value in range from 0.0 to 1.0
     *
     * @since 2.0.0
     */
    public float blue() {
        return blue;
    }

    /**
     * Gets this color's alpha channel.
     *
     * <p>
     * Equivalent to:
     *
     * <pre>{@code
     * 1 - transparency()
     * }</pre>
     *
     * @return alpha color channel float value in range from 0.0 to 1.0
     *
     * @see #transparency()
     *
     * @since 2.0.0
     */
    public float alpha() {
        return alpha;
    }

    /**
     * Gets this color's transparency channel.
     *
     * <p>
     * Equivalent to:
     *
     * <pre>{@code
     * 1 - alpha()
     * }</pre>
     *
     * @return transparency color channel float value in range from 0.0 to 1.0
     *
     * @see #alpha()
     *
     * @since 2.0.0
     */
    public float transparency() {
        return 1f - alpha;
    }

    /**
     * Gets a {@link javafx.scene.paint.Color Color} representing this color.
     *
     * <p>
     * Creates a new instance everytime.
     *
     * @return JavaFX's {@code Color} class with equivalent color channels
     *
     * @see javafx.scene.paint.Color
     *
     * @since 2.0.0
     */
    public Color jfxColor() {
        return new Color(red, green, blue, alpha);
    }
}
