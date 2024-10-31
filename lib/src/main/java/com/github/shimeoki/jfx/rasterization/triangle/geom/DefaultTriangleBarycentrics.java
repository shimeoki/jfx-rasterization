package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.math.Floats;

public class DefaultTriangleBarycentrics implements TriangleBarycentrics {

    private final float l1;
    private final float l2;
    private final float l3;

    private final boolean inside;

    public DefaultTriangleBarycentrics(
            final float l1, final float l2, final float l3) {

        final float sum = l1 + l2 + l3;
        if (!Floats.equals(sum, 1)) {
            throw new IllegalArgumentException("coordinates are not normalized");
        }

        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;

        this.inside = computeInside();
    }

    @Override
    public float lambda1() {
        return l1;
    }

    @Override
    public float lambda2() {
        return l2;
    }

    @Override
    public float lambda3() {
        return l3;
    }

    private boolean computeInside() {
        final boolean b1 = Floats.moreThan(l1, 0);
        final boolean b2 = Floats.moreThan(l2, 0);
        final boolean b3 = Floats.moreThan(l3, 0);

        return b1 && b2 && b3;
    }

    @Override
    public boolean inside() {
        return inside;
    }
}