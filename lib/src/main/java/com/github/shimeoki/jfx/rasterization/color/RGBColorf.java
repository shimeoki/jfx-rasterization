package com.github.shimeoki.jfx.rasterization.color;

import com.github.shimeoki.jfx.rasterization.math.Floats;

import javafx.scene.paint.Color;

public final class RGBColorf implements Colorf {

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    private final int argb;

    public RGBColorf(
            final float r, final float g, final float b, final float a) {

        this.r = Floats.confined(0, r, 1);
        this.g = Floats.confined(0, g, 1);
        this.b = Floats.confined(0, b, 1);
        this.a = Floats.confined(0, a, 1);

        argb = computeArgb();
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

    private int computeArgb() {
        final int alpha = (int) (a * 255);
        final int red = (int) (r * 255);
        final int green = (int) (g * 255);
        final int blue = (int) (b * 255);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    @Override
    public int argb() {
        return argb;
    }
}
