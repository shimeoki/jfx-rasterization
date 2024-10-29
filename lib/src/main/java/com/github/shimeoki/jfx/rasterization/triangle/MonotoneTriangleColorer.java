package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import javafx.scene.paint.Color;

public class MonotoneTriangleColorer implements TriangleColorer {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    public MonotoneTriangleColorer() {
    }

    public MonotoneTriangleColorer(final Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        Objects.requireNonNull(color);

        this.color = color;
    }

    @Override
    public Color get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return getColor();
    }
}
