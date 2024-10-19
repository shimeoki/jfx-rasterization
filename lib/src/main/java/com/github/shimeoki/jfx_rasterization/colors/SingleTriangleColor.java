package com.github.shimeoki.jfx_rasterization.colors;

import com.github.shimeoki.jfx_rasterization.TriangleColor;

import javafx.scene.paint.Color;

public class SingleTriangleColor implements TriangleColor {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    public SingleTriangleColor(final Color color) {
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color = color;
    }

    // epsilon?
    private boolean validCoordinate(final double lambda) {
        return (0 <= lambda) && (lambda <= 1);
    }

    private boolean validCoordinates(final double l1, final double l2, final double l3) {
        final boolean r1 = validCoordinate(l1);
        final boolean r2 = validCoordinate(l2);
        final boolean r3 = validCoordinate(l3);

        return r1 && r2 && r3;
    }

    @Override
    public Color get(final double l1, final double l2, final double l3) {
        if (!validCoordinates(l1, l2, l3)) {
            return null;
        }

        return getColor();
    }
}
