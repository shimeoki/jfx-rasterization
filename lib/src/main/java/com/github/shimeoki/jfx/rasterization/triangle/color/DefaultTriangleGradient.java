package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;

public final class DefaultTriangleGradient implements TriangleGradient {

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
    public Colorf color2() {
        return color2;
    }

    @Override
    public Colorf color3() {
        return color3;
    }
}
