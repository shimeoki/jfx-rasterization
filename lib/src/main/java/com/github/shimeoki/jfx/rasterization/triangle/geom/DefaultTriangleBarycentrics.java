package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.math.Floats;

public class DefaultTriangleBarycentrics implements TriangleBarycentrics {

    private final float lambda1;
    private final float lambda2;
    private final float lambda3;

    private final boolean inside;

    public DefaultTriangleBarycentrics(
            final float lambda1, final float lambda2, final float lambda3) {

        final float sum = lambda1 + lambda2 + lambda3;
        if (!Floats.equals(sum, 1)) {
            throw new IllegalArgumentException("coordinates are not normalized");
        }

        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;

        inside = computeInside();
    }

    @Override
    public float lambda1() {
        return lambda1;
    }

    @Override
    public float lambda2() {
        return lambda2;
    }

    @Override
    public float lambda3() {
        return lambda3;
    }

    private boolean computeInside() {
        final boolean ok1 = Floats.moreThan(lambda1, 0f);
        final boolean ok2 = Floats.moreThan(lambda2, 0f);
        final boolean ok3 = Floats.moreThan(lambda3, 0f);

        return ok1 && ok2 && ok3;
    }

    @Override
    public boolean inside() {
        return inside;
    }
}
