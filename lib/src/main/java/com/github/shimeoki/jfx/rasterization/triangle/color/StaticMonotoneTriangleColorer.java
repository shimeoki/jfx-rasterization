package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public class StaticMonotoneTriangleColorer implements TriangleColorer {

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public StaticMonotoneTriangleColorer(final Colorf c) {
        Objects.requireNonNull(c);

        red = c.red();
        green = c.green();
        blue = c.blue();
        alpha = c.alpha();
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new RGBColor(red, green, blue, alpha);
    }
}
