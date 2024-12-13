package io.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.Colorf;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public final class MonotoneTriangleColorer implements TriangleColorer {

    private final Colorf color;

    public MonotoneTriangleColorer(final Colorf c) {
        color = Objects.requireNonNull(c);
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return color;
    }
}
