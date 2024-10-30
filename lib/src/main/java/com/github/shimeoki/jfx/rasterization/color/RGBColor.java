package com.github.shimeoki.jfx.rasterization.color;

import com.github.shimeoki.jfx.rasterization.math.FloatMath;

import javafx.scene.paint.Color;

public class RGBColor implements Colorf {

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    public RGBColor(
            final float r, final float g, final float b, final float a) {

        this.r = FloatMath.confined(0, r, 1);
        this.g = FloatMath.confined(0, g, 1);
        this.b = FloatMath.confined(0, b, 1);
        this.a = FloatMath.confined(0, a, 1);
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
    public float opacity() {
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
