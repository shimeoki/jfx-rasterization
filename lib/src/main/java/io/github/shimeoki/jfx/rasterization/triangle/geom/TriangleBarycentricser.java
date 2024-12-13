package io.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
import io.github.shimeoki.jfx.rasterization.math.Floats;

public final class TriangleBarycentricser {

    private final Triangle triangle;
    private final TriangleBarycentrics barycentrics;

    // on update()

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    private float x3;
    private float y3;

    private float dx13;
    private float dy13;

    private float dx23;
    private float dy23;

    private float k; // inverted denominator

    // on calculate()

    private float x;
    private float y;

    private float dx3;
    private float dy3;

    private float numerator1;
    private float numerator2;
    private float numerator3;

    public TriangleBarycentricser(final Triangle t) {
        this.triangle = Objects.requireNonNull(t);
        barycentrics = new TriangleBarycentrics(0, 0, 0);
        update();
    }

    public TriangleBarycentrics barycentrics() {
        return barycentrics;
    }

    public void update() {
        x1 = triangle.v1().x();
        y1 = triangle.v1().y();

        x2 = triangle.v2().x();
        y2 = triangle.v2().y();

        x3 = triangle.v3().x();
        y3 = triangle.v3().y();

        dx13 = x1 - x3;
        dy13 = y1 - y3;

        dx23 = x2 - x3;
        dy23 = y2 - y3;

        k = dy23 * dx13 - dx23 * dy13; // denominator
        if (Floats.equals(k, 0)) {
            k = 0;
        } else {
            k = 1f / k;
        }
    }

    public void calculate(final Pos2f p) {
        Objects.requireNonNull(p);

        x = p.x();
        y = p.y();

        dx3 = x - x3;
        dy3 = y - y3;

        numerator1 = dy23 * dx3 - dx23 * dy3;
        numerator2 = -dy13 * dx3 + dx13 * dy3;
        numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        barycentrics.setLambda1(numerator1 * k);
        barycentrics.setLambda2(numerator2 * k);
        barycentrics.setLambda3(numerator3 * k);
    }
}
