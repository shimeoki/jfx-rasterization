package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColor;
import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

public class GradientTriangleColorer implements TriangleColorer {

    private final TriangleGradient g;

    public GradientTriangleColorer(final TriangleGradient g) {
        Objects.requireNonNull(g);

        this.g = g;
    }

    private float red(final TriangleBarycentrics b) {
        final float r1 = b.lambda1() * g.red1();
        final float r2 = b.lambda2() * g.red2();
        final float r3 = b.lambda3() * g.red3();

        return Floats.confined(0, r1 + r2 + r3, 1);
    }

    private float green(final TriangleBarycentrics b) {
        final float g1 = b.lambda1() * g.green1();
        final float g2 = b.lambda2() * g.green2();
        final float g3 = b.lambda3() * g.green3();

        return Floats.confined(0, g1 + g2 + g3, 1);
    }

    private float blue(final TriangleBarycentrics b) {
        final float b1 = b.lambda1() * g.blue1();
        final float b2 = b.lambda2() * g.blue2();
        final float b3 = b.lambda3() * g.blue3();

        return Floats.confined(0, b1 + b2 + b3, 1);
    }

    private float opacity(final TriangleBarycentrics b) {
        final float o1 = b.lambda1() * g.alpha1();
        final float o2 = b.lambda2() * g.alpha2();
        final float o3 = b.lambda3() * g.alpha3();

        return Floats.confined(0, o1 + o2 + o3, 1);
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new RGBColor(red(b), green(b), blue(b), opacity(b));
    }
}
