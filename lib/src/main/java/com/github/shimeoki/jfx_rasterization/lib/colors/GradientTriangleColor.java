package com.github.shimeoki.jfx_rasterization.lib.colors;

import java.util.Objects;

import com.github.shimeoki.jfx_rasterization.lib.TriangleColor;

import javafx.scene.paint.Color;

public class GradientTriangleColor implements TriangleColor {

    public static final double EPSILON = 0.0001;

    public static final Color DEFAULT_COLOR_1 = Color.RED;
    public static final Color DEFAULT_COLOR_2 = Color.FORESTGREEN;
    public static final Color DEFAULT_COLOR_3 = Color.BLUE;

    private Color color1 = DEFAULT_COLOR_1;
    private Color color2 = DEFAULT_COLOR_2;
    private Color color3 = DEFAULT_COLOR_3;

    public GradientTriangleColor() {
    }

    public GradientTriangleColor(
            final Color color1, final Color color2, final Color color3) {

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
        Objects.requireNonNull(color);

        this.color1 = color;
    }

    public void setColor2(final Color color) {
        Objects.requireNonNull(color);

        this.color2 = color;
    }

    public void setColor3(final Color color) {
        Objects.requireNonNull(color);

        this.color3 = color;
    }

    private boolean validCoordinates(
            final double l1, final double l2, final double l3) {

        final double sum = l1 + l2 + l3;

        return (1 - EPSILON <= sum) && (sum <= 1 + EPSILON);
    }

    private double getRed(final double l1, final double l2, final double l3) {
        final double r1 = l1 * getColor1().getRed();
        final double r2 = l2 * getColor2().getRed();
        final double r3 = l3 * getColor3().getRed();

        final double r = r1 + r2 + r3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, r), 1);
    }

    private double getGreen(final double l1, final double l2, final double l3) {
        final double g1 = l1 * getColor1().getGreen();
        final double g2 = l2 * getColor2().getGreen();
        final double g3 = l3 * getColor3().getGreen();

        final double g = g1 + g2 + g3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, g), 1);
    }

    private double getBlue(final double l1, final double l2, final double l3) {
        final double b1 = l1 * getColor1().getBlue();
        final double b2 = l2 * getColor2().getBlue();
        final double b3 = l3 * getColor3().getBlue();

        final double b = b1 + b2 + b3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, b), 1);
    }

    private double getOpacity(final double l1, final double l2, final double l3) {
        final double o1 = l1 * getColor1().getOpacity();
        final double o2 = l2 * getColor2().getOpacity();
        final double o3 = l3 * getColor3().getOpacity();

        final double o = o1 + o2 + o3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, o), 1);
    }

    @Override
    public Color get(final double l1, final double l2, final double l3) {
        if (!validCoordinates(l1, l2, l3)) {
            throw new IllegalArgumentException("coordinates are not valid");
        }

        final double r = getRed(l1, l2, l3);
        final double g = getGreen(l1, l2, l3);
        final double b = getBlue(l1, l2, l3);
        final double o = getOpacity(l1, l2, l3);

        return new Color(r, g, b, o);
    }
}
