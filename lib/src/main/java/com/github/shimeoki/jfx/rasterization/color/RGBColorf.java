package com.github.shimeoki.jfx.rasterization.color;

import com.github.shimeoki.jfx.rasterization.math.Floats;

import javafx.scene.paint.Color;

public final class RGBColorf implements Colorf {

    private final float r;
    private final float g;
    private final float b;
    private final float a;

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
