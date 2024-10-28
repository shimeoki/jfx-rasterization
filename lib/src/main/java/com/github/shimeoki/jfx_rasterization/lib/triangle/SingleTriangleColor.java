package com.github.shimeoki.jfx_rasterization.lib.triangle;

import java.util.Objects;

import com.github.shimeoki.jfx_rasterization.lib.Arithmetic;

import javafx.scene.paint.Color;

public class SingleTriangleColor implements TriangleColor {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    public SingleTriangleColor() {
    }

    public SingleTriangleColor(final Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        Objects.requireNonNull(color);

        this.color = color;
    }

    private boolean validCoordinates(
            final double l1, final double l2, final double l3) {

        final double sum = l1 + l2 + l3;

        return Arithmetic.equals(sum, 1);
    }

    @Override
    public Color get(final double l1, final double l2, final double l3) {
        if (!validCoordinates(l1, l2, l3)) {
            throw new IllegalArgumentException("coordinates are not valid");
        }

        return getColor();
    }
}
