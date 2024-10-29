package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.DoubleArithmetic;

import javafx.scene.paint.Color;

public class GradientTriangleColorer implements TriangleColorer {

    public static final Color DEFAULT_COLOR_1 = Color.RED;
    public static final Color DEFAULT_COLOR_2 = Color.FORESTGREEN;
    public static final Color DEFAULT_COLOR_3 = Color.BLUE;

    private Color c1 = DEFAULT_COLOR_1;
    private Color c2 = DEFAULT_COLOR_2;
    private Color c3 = DEFAULT_COLOR_3;

    public GradientTriangleColorer() {
    }

    public GradientTriangleColorer(
            final Color color1, final Color color2, final Color color3) {

        setColor1(color1);
        setColor2(color2);
        setColor3(color3);
    }

    public Color getColor1() {
        return c1;
    }

    public Color getColor2() {
        return c2;
    }

    public Color getColor3() {
        return c3;
    }

    public void setColor1(final Color c) {
        Objects.requireNonNull(c);

        c1 = c;
    }

    public void setColor2(final Color c) {
        Objects.requireNonNull(c);

        c2 = c;
    }

    public void setColor3(final Color c) {
        Objects.requireNonNull(c);

        c3 = c;
    }

    private double red(final TriangleBarycentrics b) {
        final double r1 = b.lambda1() * c1.getRed();
        final double r2 = b.lambda2() * c2.getRed();
        final double r3 = b.lambda3() * c3.getRed();

        return DoubleArithmetic.confined(0, r1 + r2 + r3, 1);
    }

    private double green(final TriangleBarycentrics b) {
        final double g1 = b.lambda1() * c1.getGreen();
        final double g2 = b.lambda2() * c2.getGreen();
        final double g3 = b.lambda3() * c3.getGreen();

        return DoubleArithmetic.confined(0, g1 + g2 + g3, 1);
    }

    private double blue(final TriangleBarycentrics b) {
        final double b1 = b.lambda1() * c1.getBlue();
        final double b2 = b.lambda2() * c2.getBlue();
        final double b3 = b.lambda3() * c3.getBlue();

        return DoubleArithmetic.confined(0, b1 + b2 + b3, 1);
    }

    private double opacity(final TriangleBarycentrics b) {
        final double o1 = b.lambda1() * c1.getOpacity();
        final double o2 = b.lambda2() * c2.getOpacity();
        final double o3 = b.lambda3() * c3.getOpacity();

        return DoubleArithmetic.confined(0, o1 + o2 + o3, 1);
    }

    @Override
    public Color get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new Color(red(b), green(b), blue(b), opacity(b));
    }
}
