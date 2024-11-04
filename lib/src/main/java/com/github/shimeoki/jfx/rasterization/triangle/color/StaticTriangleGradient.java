package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;

public final class StaticTriangleGradient implements TriangleGradient {

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

    public StaticTriangleGradient(
            final Colorf c1, final Colorf c2, final Colorf c3) {

        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        red1 = c1.red();
        red2 = c2.red();
        red3 = c3.red();

        green1 = c1.green();
        green2 = c2.green();
        green3 = c3.green();

        blue1 = c1.blue();
        blue2 = c2.blue();
        blue3 = c3.blue();

        alpha1 = c1.alpha();
        alpha2 = c2.alpha();
        alpha3 = c3.alpha();
    }

    @Override
    public Colorf color1() {
        return new RGBColor(red1, green1, blue1, alpha1);
    }

    @Override
    public Colorf color2() {
        return new RGBColor(red2, green2, blue2, alpha2);
    }

    @Override
    public Colorf color3() {
        return new RGBColor(red3, green3, blue3, alpha3);
    }
}
