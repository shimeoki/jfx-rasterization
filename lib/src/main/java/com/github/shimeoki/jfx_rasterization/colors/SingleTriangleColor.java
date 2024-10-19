package com.github.shimeoki.jfx_rasterization.colors;

import com.github.shimeoki.jfx_rasterization.TriangleColor;

import javafx.scene.paint.Color;

public class SingleTriangleColor implements TriangleColor {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    public SingleTriangleColor(Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color = color;
    }

    // epsilon?
    private boolean validCoordinate(double lambda) {
        return (0 <= lambda) && (lambda <= 1);
    }

    @Override
    public Color get(final double l1, final double l2, final double l3) {
        if (!validCoordinate(l1)) {
            return null;
        }

        if (!validCoordinate(l2)) {
            return null;
        }

        if (!validCoordinate(l3)) {
            return null;
        }

        return getColor();
    }
}
