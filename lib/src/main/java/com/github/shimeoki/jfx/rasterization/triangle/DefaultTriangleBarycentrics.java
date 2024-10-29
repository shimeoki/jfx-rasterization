package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.DoubleMath;

public class DefaultTriangleBarycentrics implements TriangleBarycentrics {

    private final double l1;
    private final double l2;
    private final double l3;

    public DefaultTriangleBarycentrics(
            final double l1, final double l2, final double l3) {

        final double sum = l1 + l2 + l3;
        if (!DoubleMath.equals(sum, 1)) {
            throw new IllegalArgumentException("coordinates are not normalized");
        }

        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    @Override
    public double lambda1() {
        return l1;
    }

    @Override
    public double lambda2() {
        return l2;
    }

    @Override
    public double lambda3() {
        return l3;
    }

    @Override
    public boolean inside() {
        final boolean b1 = DoubleMath.moreThan(l1, 0);
        final boolean b2 = DoubleMath.moreThan(l2, 0);
        final boolean b3 = DoubleMath.moreThan(l3, 0);

        return b1 && b2 && b3;
    }
}
