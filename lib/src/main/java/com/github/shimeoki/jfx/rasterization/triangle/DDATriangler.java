package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.DoubleMath;
import com.github.shimeoki.jfx.rasterization.DoublePoint2;
import com.github.shimeoki.jfx.rasterization.Point;

import javafx.scene.image.PixelWriter;

public class DDATriangler implements Triangler {

    private PixelWriter w;
    private TriangleColorer c;

    public DDATriangler(final PixelWriter w, final TriangleColorer c) {
        setPixelWriter(w);
        setColorer(c);
    }

    @Override
    public PixelWriter getPixelWriter() {
        return w;
    }

    @Override
    public void setPixelWriter(final PixelWriter w) {
        Objects.requireNonNull(w);

        this.w = w;
    }

    @Override
    public TriangleColorer getColorer() {
        return c;
    }

    @Override
    public void setColorer(final TriangleColorer c) {
        Objects.requireNonNull(c);

        this.c = c;
    }

    private List<DoublePoint2> sortedVertices(final Triangle t) {
        final List<DoublePoint2> vertices = new ArrayList<>();
        vertices.add(t.v1());
        vertices.add(t.v2());
        vertices.add(t.v3());

        vertices.sort(Comparator
                .comparing(DoublePoint2::y)
                .thenComparing(DoublePoint2::x));

        return vertices;
    }

    private void drawFlat(final Triangle t,
            final DoublePoint2 lone,
            final DoublePoint2 flat1,
            final DoublePoint2 flat2) {

        final double lx = lone.x();
        final double ly = lone.y();

        // "delta flat x1"
        final double dfx1 = flat1.x() - lx;
        final double dfy1 = flat1.y() - ly;

        final double dfx2 = flat2.x() - lx;
        final double dfy2 = flat2.y() - ly;

        double dx1 = dfx1 / dfy1;
        double dx2 = dfx2 / dfy2;

        final double fy = flat1.y();
        if (DoubleMath.moreThan(ly, fy)) {
            drawFlatMin(t, lone, fy, dx1, dx2);
        } else {
            drawFlatMax(t, lone, fy, dx1, dx2);
        }
    }

    private void drawFlatMax(final Triangle t,
            final DoublePoint2 v,
            final double maxY,
            final double dx1,
            final double dx2) {

        double x1 = v.x();
        double x2 = x1;

        for (int y = (int) v.y(); y <= maxY; y++) {
            // round doubles instead of floor?
            drawHLine(t, (int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatMin(final Triangle t,
            final DoublePoint2 v,
            final double minY,
            final double dx1,
            final double dx2) {

        double x1 = v.x();
        double x2 = x1;

        for (int y = (int) v.y(); y > minY; y--) {
            // round doubles instead of floor?
            drawHLine(t, (int) x1, (int) x2, y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine(final Triangle t,
            final int x1,
            final int x2,
            final int y) {

        for (int x = (int) x1; x <= x2; x++) {
            final TriangleBarycentrics b;
            try {
                b = t.barycentrics(new Point(x, y));
            } catch (Exception e) {
                continue;
            }

            if (!b.inside()) {
                continue;
            }

            w.setColor(x, y, c.get(b));
        }
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        // docs:
        // https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html

        final List<DoublePoint2> vertices = sortedVertices(t);

        final DoublePoint2 v1 = vertices.get(0);
        final DoublePoint2 v2 = vertices.get(1);
        final DoublePoint2 v3 = vertices.get(2);

        final double x1 = v1.x();
        final double y1 = v1.y();

        final double x2 = v2.x();
        final double y2 = v2.y();

        final double x3 = v3.x();
        final double y3 = v3.y();

        if (DoubleMath.equals(y2, y3)) {
            drawFlat(t, v1, v2, v3);
            return;
        }

        if (DoubleMath.equals(y1, y2)) {
            drawFlat(t, v3, v1, v2);
            return;
        }

        final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final DoublePoint2 v4 = new Point(x4, v2.y());

        // non strict equality?
        if (DoubleMath.moreThan(x4, x2)) {
            drawFlat(t, v1, v2, v4);
            drawFlat(t, v3, v2, v4);
        } else {
            drawFlat(t, v1, v4, v2);
            drawFlat(t, v3, v4, v2);
        }
    }
}
