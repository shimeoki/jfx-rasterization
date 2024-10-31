package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;

public class DefaultTriangleGradient implements TriangleGradient {

    private final Colorf c1;
    private final Colorf c2;
    private final Colorf c3;

    public DefaultTriangleGradient(
            final Colorf c1, final Colorf c2, final Colorf c3) {

        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    @Override
    public Colorf color1() {
        return c1;
    }

    @Override
    public float red1() {
        return c1.red();
    }

    @Override
    public float green1() {
        return c1.green();
    }

    @Override
    public float blue1() {
        return c1.blue();
    }

    @Override
    public float alpha1() {
        return c1.alpha();
    }

    @Override
    public Colorf color2() {
        return c2;
    }

    @Override
    public float red2() {
        return c2.red();
    }

    @Override
    public float green2() {
        return c2.green();
    }

    @Override
    public float blue2() {
        return c2.blue();
    }

    @Override
    public float alpha2() {
        return c2.alpha();
    }

    @Override
    public Colorf color3() {
        return c3;
    }

    @Override
    public float red3() {
        return c3.red();
    }

    @Override
    public float green3() {
        return c3.green();
    }

    @Override
    public float blue3() {
        return c3.blue();
    }

    @Override
    public float alpha3() {
        return c3.alpha();
    }
}
