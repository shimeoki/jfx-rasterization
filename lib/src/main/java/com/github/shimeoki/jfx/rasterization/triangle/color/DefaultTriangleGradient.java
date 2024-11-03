package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;

public class DefaultTriangleGradient implements TriangleGradient {

    private final Colorf color1;
    private final Colorf color2;
    private final Colorf color3;

    public DefaultTriangleGradient(
            final Colorf c1, final Colorf c2, final Colorf c3) {

        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        color1 = c1;
        color2 = c2;
        color3 = c3;
    }

    @Override
    public Colorf color1() {
        return color1;
    }

    @Override
    public float red1() {
        return color1.red();
    }

    @Override
    public float green1() {
        return color1.green();
    }

    @Override
    public float blue1() {
        return color1.blue();
    }

    @Override
    public float alpha1() {
        return color1.alpha();
    }

    @Override
    public Colorf color2() {
        return color2;
    }

    @Override
    public float red2() {
        return color2.red();
    }

    @Override
    public float green2() {
        return color2.green();
    }

    @Override
    public float blue2() {
        return color2.blue();
    }

    @Override
    public float alpha2() {
        return color2.alpha();
    }

    @Override
    public Colorf color3() {
        return color3;
    }

    @Override
    public float red3() {
        return color3.red();
    }

    @Override
    public float green3() {
        return color3.green();
    }

    @Override
    public float blue3() {
        return color3.blue();
    }

    @Override
    public float alpha3() {
        return color3.alpha();
    }
}
