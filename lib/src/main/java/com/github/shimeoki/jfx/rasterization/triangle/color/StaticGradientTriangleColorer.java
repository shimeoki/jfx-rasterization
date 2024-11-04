package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;
import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public final class StaticGradientTriangleColorer implements TriangleColorer {

    private final float red1;
    private final float red2;
    private final float red3;

    private final float green1;
    private final float green2;
    private final float green3;

    private final float blue1;
    private final float blue2;
    private final float blue3;

    private final float alpha1;
    private final float alpha2;
    private final float alpha3;

    private float lambda1 = 0;
    private float lambda2 = 0;
    private float lambda3 = 0;

    public StaticGradientTriangleColorer(final TriangleGradient g) {
        Objects.requireNonNull(g);

        final Colorf color1 = g.color1();
        final Colorf color2 = g.color2();
        final Colorf color3 = g.color3();

        red1 = color1.red();
        red2 = color2.red();
        red3 = color3.red();

        green1 = color1.green();
        green2 = color2.green();
        green3 = color3.green();

        blue1 = color1.blue();
        blue2 = color2.blue();
        blue3 = color3.blue();

        alpha1 = color1.alpha();
        alpha2 = color2.alpha();
        alpha3 = color3.alpha();
    }

    private float red() {
        final float r1 = lambda1 * red1;
        final float r2 = lambda2 * red2;
        final float r3 = lambda3 * red3;

        return Floats.confined(0, r1 + r2 + r3, 1);
    }

    private float green() {
        final float g1 = lambda1 * green1;
        final float g2 = lambda2 * green2;
        final float g3 = lambda3 * green3;

        return Floats.confined(0, g1 + g2 + g3, 1);
    }

    private float blue() {
        final float b1 = lambda1 * blue1;
        final float b2 = lambda2 * blue2;
        final float b3 = lambda3 * blue3;

        return Floats.confined(0, b1 + b2 + b3, 1);
    }

    private float alpha() {
        final float a1 = lambda1 * alpha1;
        final float a2 = lambda2 * alpha2;
        final float a3 = lambda3 * alpha3;

        return Floats.confined(0, a1 + a2 + a3, 1);
    }

    private void cache(final TriangleBarycentrics b) {
        lambda1 = b.lambda1();
        lambda2 = b.lambda2();
        lambda3 = b.lambda3();
    }

    private void uncache() {
        lambda1 = 0;
        lambda2 = 0;
        lambda3 = 0;
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
