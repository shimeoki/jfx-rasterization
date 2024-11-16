package com.github.shimeoki.jfx.rasterization.color;

import com.github.shimeoki.jfx.rasterization.math.Floats;

import javafx.scene.paint.Color;

/**
 * Standard implementation of the {@code Colorf}.
 *
 * <p>
 * Uses 4 (RGBA) color channels as floats to store the color.
 * <p>
 * The color is non-modifiable, so the object caches all the necessary values at
 * the moment of creation.
 *
 * @since 1.0.0
 *
 * @see Colorf
 */
public final class RGBColorf implements Colorf {

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    /**
     * Creates a new {@code RGBColorf} instance.
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
    public RGBColorf(
            final float r, final float g, final float b, final float a) {

        this.r = Floats.confined(0, r, 1);
        this.g = Floats.confined(0, g, 1);
        this.b = Floats.confined(0, b, 1);
        this.a = Floats.confined(0, a, 1);
    }

    @Override
    public float red() {
        return r;
    }

    @Override
    public float green() {
        return g;
    }

    @Override
    public float blue() {
        return b;
    }

    @Override
    public float alpha() {
        return a;
    }

    @Override
    public float transparency() {
        return 1f - a;
    }

    @Override
    public Color jfxColor() {
        return new Color(r, g, b, a);
    }
}
