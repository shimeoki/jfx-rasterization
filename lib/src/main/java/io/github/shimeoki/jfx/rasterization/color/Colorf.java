package io.github.shimeoki.jfx.rasterization.color;

import io.github.shimeoki.jfx.rasterization.math.Floats;

import javafx.scene.paint.Color;

/**
 * Simple color class with float values.
 *
 * <p>
 * Uses 4 (RGBA) color channels as floats to store the color.
 * <p>
 * The color is non-modifiable, so the object caches all the necessary values at
 * the moment of creation.
 *
 * @since 1.0.0
 *
 * @see HTMLColorf
 */
public final class Colorf {

    private final float r;
    private final float g;
    private final float b;
    private final float a;

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
     * @since 1.0.0
     *
     * @see Floats#confined(float, float, float)
     */
    public Colorf(
            final float r, final float g, final float b, final float a) {

        this.r = Floats.confined(0, r, 1);
        this.g = Floats.confined(0, g, 1);
        this.b = Floats.confined(0, b, 1);
        this.a = Floats.confined(0, a, 1);
    }

    /**
     * Gets this color's red channel.
     *
     * @return red color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float red() {
        return r;
    }

    /**
     * Gets this color's green channel.
     *
     * @return green color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float green() {
        return g;
    }

    /**
     * Gets this color's blue channel.
     *
     * @return blue color channel float value in range from 0.0 to 1.0
     *
     * @since 1.0.0
     */
    public float blue() {
        return b;
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
     * @since 1.0.0
     */
    public float alpha() {
        return a;
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
     * @since 1.0.0
     */
    public float transparency() {
        return 1f - a;
    }

    /**
     * Gets a {@link javafx.scene.paint.Color Color} representing this color.
     *
     * @return JavaFX's {@code Color} class with equivalent color channels
     *
     * @see javafx.scene.paint.Color
     */
    public Color jfxColor() {
        return new Color(r, g, b, a);
    }
}
