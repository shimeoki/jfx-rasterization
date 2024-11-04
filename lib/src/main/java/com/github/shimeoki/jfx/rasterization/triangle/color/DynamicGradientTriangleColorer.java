package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;
import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public final class DynamicGradientTriangleColorer implements TriangleColorer {

    private final TriangleGradient gradient;

    private float lambda1 = 0;
    private float lambda2 = 0;
    private float lambda3 = 0;

    private Colorf color1 = null;
    private Colorf color2 = null;
    private Colorf color3 = null;

    public DynamicGradientTriangleColorer(final TriangleGradient g) {
        Objects.requireNonNull(g);

        gradient = g;
    }

    private void cache(final TriangleBarycentrics b) {
        lambda1 = b.lambda1();
        lambda2 = b.lambda2();
        lambda3 = b.lambda3();

        color1 = gradient.color1();
        color2 = gradient.color2();
        color3 = gradient.color3();
    }

    private void uncache() {
        lambda1 = 0;
        lambda2 = 0;
        lambda3 = 0;

        color1 = null;
        color2 = null;
        color3 = null;
    }

    private float red() {
        final float r1 = lambda1 * color1.red();
        final float r2 = lambda2 * color2.red();
        final float r3 = lambda3 * color3.red();

        return Floats.confined(0, r1 + r2 + r3, 1);
    }

    private float green() {
        final float g1 = lambda1 * color1.green();
        final float g2 = lambda2 * color2.green();
        final float g3 = lambda3 * color3.green();

        return Floats.confined(0, g1 + g2 + g3, 1);
    }

    private float blue() {
        final float b1 = lambda1 * color1.blue();
        final float b2 = lambda2 * color2.blue();
        final float b3 = lambda3 * color3.blue();

        return Floats.confined(0, b1 + b2 + b3, 1);
    }

    private float alpha() {
        final float a1 = lambda1 * color1.alpha();
        final float a2 = lambda2 * color2.alpha();
        final float a3 = lambda3 * color3.alpha();

        return Floats.confined(0, a1 + a2 + a3, 1);
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        cache(b);
        final Colorf c = new RGBColor(red(), green(), blue(), alpha());
        uncache();

        return c;
    }
}
