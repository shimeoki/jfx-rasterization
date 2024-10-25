package com.github.shimeoki.jfx_rasterization.lib.colors;

import com.github.shimeoki.jfx_rasterization.lib.TriangleColor;

import javafx.scene.paint.Color;

public class GradientTriangleColor implements TriangleColor {

    public static final Color DEFAULT_COLOR_1 = Color.RED;
    public static final Color DEFAULT_COLOR_2 = Color.FORESTGREEN;
    public static final Color DEFAULT_COLOR_3 = Color.BLUE;

    private Color color1 = DEFAULT_COLOR_1;
    private Color color2 = DEFAULT_COLOR_2;
    private Color color3 = DEFAULT_COLOR_3;

    public GradientTriangleColor(final Color color1, final Color color2, final Color color3) {
        setColor1(color1);
        setColor2(color2);
        setColor3(color3);
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public Color getColor3() {
        return color3;
    }

    public void setColor1(final Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color1 = color;
    }

    public void setColor2(final Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color2 = color;
    }

    public void setColor3(final Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color3 = color;
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

        final Color c1 = getColor1();
        final Color c2 = getColor2();
        final Color c3 = getColor3();

        final double r = l1 * c1.getRed() + l2 * c2.getRed() + l3 * c3.getRed();
        final double g = l1 * c1.getGreen() + l2 * c2.getGreen() + l3 * c3.getGreen();
        final double b = l1 * c1.getBlue() + l2 * c2.getBlue() + l3 * c3.getBlue();
        final double o = l1 * c1.getOpacity() + l2 * c2.getOpacity() + l3 * c3.getOpacity();

        return new Color(r, g, b, o);
    }
}
