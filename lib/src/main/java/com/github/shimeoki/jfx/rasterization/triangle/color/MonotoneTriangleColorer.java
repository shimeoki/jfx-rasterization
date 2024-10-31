package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public class MonotoneTriangleColorer implements TriangleColorer {

    private final Colorf c;

    public MonotoneTriangleColorer(final Colorf c) {
        Objects.requireNonNull(c);

        this.c = c;
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return c;
    }
}
