package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.math.Doubles;

import javafx.scene.paint.Color;

public class GradientTriangleColorer implements TriangleColorer {

    private final Color c1;
    private final Color c2;
    private final Color c3;

    public GradientTriangleColorer(
            final Color c1, final Color c2, final Color c3) {

        Objects.requireNonNull(c1);
        Objects.requireNonNull(c2);
        Objects.requireNonNull(c3);

        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    private double red(final TriangleBarycentrics b) {
        final double r1 = b.lambda1() * c1.getRed();
        final double r2 = b.lambda2() * c2.getRed();
        final double r3 = b.lambda3() * c3.getRed();

        return Doubles.confined(0, r1 + r2 + r3, 1);
    }

    private double green(final TriangleBarycentrics b) {
        final double g1 = b.lambda1() * c1.getGreen();
        final double g2 = b.lambda2() * c2.getGreen();
        final double g3 = b.lambda3() * c3.getGreen();

        return Doubles.confined(0, g1 + g2 + g3, 1);
    }

    private double blue(final TriangleBarycentrics b) {
        final double b1 = b.lambda1() * c1.getBlue();
        final double b2 = b.lambda2() * c2.getBlue();
        final double b3 = b.lambda3() * c3.getBlue();

        return Doubles.confined(0, b1 + b2 + b3, 1);
    }

    private double opacity(final TriangleBarycentrics b) {
        final double o1 = b.lambda1() * c1.getOpacity();
        final double o2 = b.lambda2() * c2.getOpacity();
        final double o3 = b.lambda3() * c3.getOpacity();

        return Doubles.confined(0, o1 + o2 + o3, 1);
    }

    @Override
    public Color get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new Color(red(b), green(b), blue(b), opacity(b));
    }
}
