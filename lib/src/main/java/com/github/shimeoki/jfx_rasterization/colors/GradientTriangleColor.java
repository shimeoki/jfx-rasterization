package com.github.shimeoki.jfx_rasterization.colors;

import com.github.shimeoki.jfx_rasterization.TriangleColor;

import javafx.scene.paint.Color;

public class GradientTriangleColor implements TriangleColor {

    public static final Color DEFAULT_COLOR_1 = Color.RED;
    public static final Color DEFAULT_COLOR_2 = Color.FORESTGREEN;
    public static final Color DEFAULT_COLOR_3 = Color.BLUE;

    private Color color1 = DEFAULT_COLOR_1;
    private Color color2 = DEFAULT_COLOR_2;
    private Color color3 = DEFAULT_COLOR_3;

    public GradientTriangleColor(Color color1, Color color2, Color color3) {
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

    public void setColor1(Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color1 = color;
    }

    public void setColor2(Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color2 = color;
    }

    public void setColor3(Color color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color3 = color;
    }

    // epsilon?
    private boolean validCoordinate(double lambda) {
        return (0 <= lambda) && (lambda <= 1);
    }

    private boolean validCoordinates(double l1, double l2, double l3) {
        final boolean r1 = validCoordinate(l1);
        final boolean r2 = validCoordinate(l2);
        final boolean r3 = validCoordinate(l3);

        return r1 && r2 && r3;
    }

    @Override
    public Color get(double lambda1, double lambda2, double lambda3) {
        if (!validCoordinates(lambda1, lambda2, lambda3)) {
            return null;
        }

        final Color c1 = getColor1();
        final Color c2 = getColor2();
        final Color c3 = getColor3();

        final double r = lambda1 * c1.getRed() + lambda2 * c2.getRed() + lambda3 * c3.getRed();
        final double g = lambda1 * c1.getGreen() + lambda2 * c2.getGreen() + lambda3 * c3.getGreen();
        final double b = lambda1 * c1.getBlue() + lambda2 * c2.getBlue() + lambda3 * c3.getBlue();
        final double o = lambda1 * c1.getOpacity() + lambda2 * c2.getOpacity() + lambda3 * c3.getOpacity();

        return new Color(r, g, b, o);
    }
}
