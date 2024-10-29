package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.DoubleMath;

import javafx.geometry.Point2D;
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

    private List<Point2D> sortedVertices(final Triangle t) {
        final List<Point2D> vertices = new ArrayList<>();
        vertices.add(t.v1());
        vertices.add(t.v2());
        vertices.add(t.v3());

        vertices.sort(Comparator
                .comparing(Point2D::getY)
                .thenComparing(Point2D::getX));

        return vertices;
    }

    private void drawFlat(final Triangle t,
            final Point2D lone,
            final Point2D flat1,
            final Point2D flat2) {

        final double lx = lone.getX();
        final double ly = lone.getY();

        // "delta flat x1"
        final double dfx1 = flat1.getX() - lx;
        final double dfy1 = flat1.getY() - ly;

        final double dfx2 = flat2.getX() - lx;
        final double dfy2 = flat2.getY() - ly;

        double dx1 = dfx1 / dfy1;
        double dx2 = dfx2 / dfy2;

        if (DoubleMath.moreThan(ly, flat1.getY())) {
            drawFlatMin(t, lone, flat1.getY(), dx1, dx2);
        } else {
            drawFlatMax(t, lone, flat1.getY(), dx1, dx2);
        }
    }

    private void drawFlatMax(final Triangle t,
            final Point2D v,
            final double maxY,
            final double dx1,
            final double dx2) {

        double x1 = v.getX();
        double x2 = x1;

        for (int y = (int) v.getY(); y <= maxY; y++) {
            // round doubles instead of floor?
            drawHLine(t, (int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatMin(final Triangle t,
            final Point2D v,
            final double minY,
            final double dx1,
            final double dx2) {

        double x1 = v.getX();
        double x2 = x1;

        for (int y = (int) v.getY(); y > minY; y--) {
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
                b = t.barycentrics(new Point2D(x, y));
            } catch (Exception e) {
                continue;
            }

            if (!inside(b)) {
                continue;
            }

            w.setColor(x, y, c.get(b));
        }
    }

    private boolean inside(final TriangleBarycentrics b) {
        final boolean b1 = DoubleMath.moreThan(b.lambda1(), 0);
        final boolean b2 = DoubleMath.moreThan(b.lambda2(), 0);
        final boolean b3 = DoubleMath.moreThan(b.lambda3(), 0);

        return b1 && b2 && b3;
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        // docs:
        // https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html

        final List<Point2D> vertices = sortedVertices(t);

        final Point2D v1 = vertices.get(0);
        final Point2D v2 = vertices.get(1);
        final Point2D v3 = vertices.get(2);

        final double x1 = v1.getX();
        final double y1 = v1.getY();

        final double x2 = v2.getX();
        final double y2 = v2.getY();

        final double x3 = v3.getX();
        final double y3 = v3.getY();

        if (DoubleMath.equals(y2, y3)) {
            drawFlat(t, v1, v2, v3);
            return;
        }

        if (DoubleMath.equals(y1, y2)) {
            drawFlat(t, v3, v1, v2);
            return;
        }

        final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final Point2D v4 = new Point2D(x4, v2.getY());

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
