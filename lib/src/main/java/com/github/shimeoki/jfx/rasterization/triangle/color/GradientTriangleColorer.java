package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;
import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public class GradientTriangleColorer implements TriangleColorer {

    private final TriangleGradient gradient;

    public GradientTriangleColorer(final TriangleGradient g) {
        Objects.requireNonNull(g);

        gradient = g;
    }

    private float red(final TriangleBarycentrics b) {
        final float r1 = b.lambda1() * gradient.red1();
        final float r2 = b.lambda2() * gradient.red2();
        final float r3 = b.lambda3() * gradient.red3();

        return Floats.confined(0, r1 + r2 + r3, 1);
    }

    private float green(final TriangleBarycentrics b) {
        final float g1 = b.lambda1() * gradient.green1();
        final float g2 = b.lambda2() * gradient.green2();
        final float g3 = b.lambda3() * gradient.green3();

        return Floats.confined(0, g1 + g2 + g3, 1);
    }

    private float blue(final TriangleBarycentrics b) {
        final float b1 = b.lambda1() * gradient.blue1();
        final float b2 = b.lambda2() * gradient.blue2();
        final float b3 = b.lambda3() * gradient.blue3();

        return Floats.confined(0, b1 + b2 + b3, 1);
    }

    private float opacity(final TriangleBarycentrics b) {
        final float o1 = b.lambda1() * gradient.alpha1();
        final float o2 = b.lambda2() * gradient.alpha2();
        final float o3 = b.lambda3() * gradient.alpha3();

        return Floats.confined(0, o1 + o2 + o3, 1);
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new RGBColor(red(b), green(b), blue(b), opacity(b));
    }
}
