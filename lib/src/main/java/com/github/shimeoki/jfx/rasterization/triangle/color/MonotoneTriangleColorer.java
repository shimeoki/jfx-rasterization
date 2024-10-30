package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.paint.Color;

public class MonotoneTriangleColorer implements TriangleColorer {

    private final Color c;

    public MonotoneTriangleColorer(final Color c) {
        Objects.requireNonNull(c);

        this.c = c;
    }

    @Override
    public Color get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return c;
    }
}
