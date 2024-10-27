package com.github.shimeoki.jfx_rasterization.lib.colors;

import java.util.Objects;

import com.github.shimeoki.jfx_rasterization.lib.TriangleColor;

import javafx.scene.paint.Color;

public class SingleTriangleColor implements TriangleColor {

    public static final double EPSILON = 0.0001;

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

        return (1 - EPSILON <= sum) && (sum <= 1 + EPSILON);
    }

    @Override
    public Color get(final double l1, final double l2, final double l3) {
        if (!validCoordinates(l1, l2, l3)) {
            throw new IllegalArgumentException("coordinates are not valid");
        }

        return getColor();
    }
}
