package io.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.Colorf;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public final class MonotoneTriangleFiller implements TriangleFiller {

    private final Colorf color;

    public MonotoneTriangleFiller(final Colorf c) {
        color = Objects.requireNonNull(c);
    }

    @Override
    public Colorf color(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return color;
    }
}
