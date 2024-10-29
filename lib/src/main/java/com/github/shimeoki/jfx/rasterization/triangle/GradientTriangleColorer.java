package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import javafx.scene.paint.Color;

public class GradientTriangleColorer implements TriangleColorer {

    public static final Color DEFAULT_COLOR_1 = Color.RED;
    public static final Color DEFAULT_COLOR_2 = Color.FORESTGREEN;
    public static final Color DEFAULT_COLOR_3 = Color.BLUE;

    private Color color1 = DEFAULT_COLOR_1;
    private Color color2 = DEFAULT_COLOR_2;
    private Color color3 = DEFAULT_COLOR_3;

    public GradientTriangleColorer() {
    }

    public GradientTriangleColorer(
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

    private double getRed(final TriangleBarycentrics b) {
        final double r1 = b.lambda1() * getColor1().getRed();
        final double r2 = b.lambda2() * getColor2().getRed();
        final double r3 = b.lambda3() * getColor3().getRed();

        final double red = r1 + r2 + r3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, red), 1);
    }

    private double getGreen(final TriangleBarycentrics b) {
        final double g1 = b.lambda1() * getColor1().getGreen();
        final double g2 = b.lambda2() * getColor2().getGreen();
        final double g3 = b.lambda3() * getColor3().getGreen();

        final double green = g1 + g2 + g3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, green), 1);
    }

    private double getBlue(final TriangleBarycentrics b) {
        final double b1 = b.lambda1() * getColor1().getBlue();
        final double b2 = b.lambda2() * getColor2().getBlue();
        final double b3 = b.lambda3() * getColor3().getBlue();

        final double blue = b1 + b2 + b3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, blue), 1);
    }

    private double getOpacity(final TriangleBarycentrics b) {
        final double o1 = b.lambda1() * getColor1().getOpacity();
        final double o2 = b.lambda2() * getColor2().getOpacity();
        final double o3 = b.lambda3() * getColor3().getOpacity();

        final double opacity = o1 + o2 + o3;

        // should be in [0.0, 1.0]
        return Math.min(Math.max(0, opacity), 1);
    }

    @Override
    public Color get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new Color(getRed(b), getGreen(b), getBlue(b), getOpacity(b));
    }
}
