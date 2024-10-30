package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.geom.Point2D;
import com.github.shimeoki.jfx.rasterization.geom.Vector;

import javafx.scene.image.PixelWriter;

public class DDATriangler implements Triangler {

    private PixelWriter w;
    private TriangleColorer c;

    public DDATriangler(final PixelWriter w, final TriangleColorer c) {
        setPixelWriter(w);
        setColorer(c);
    }

    @Override
    public PixelWriter pixelWriter() {
        return w;
    }

    @Override
    public void setPixelWriter(final PixelWriter w) {
        Objects.requireNonNull(w);

        this.w = w;
    }

    @Override
    public TriangleColorer colorer() {
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
                .comparing(Point2D::y)
                .thenComparing(Point2D::x));

        return vertices;
    }

    private void drawFlat(final Triangle t,
            final Point2D lone,
            final Point2D flat1,
            final Point2D flat2) {

        final float lx = lone.x();
        final float ly = lone.y();

        // "delta flat x1"
        final float dfx1 = flat1.x() - lx;
        final float dfy1 = flat1.y() - ly;

        final float dfx2 = flat2.x() - lx;
        final float dfy2 = flat2.y() - ly;

        float dx1 = dfx1 / dfy1;
        float dx2 = dfx2 / dfy2;

        final float fy = flat1.y();
        if (Floats.moreThan(ly, fy)) {
            drawFlatMin(t, lone, fy, dx1, dx2);
        } else {
            drawFlatMax(t, lone, fy, dx1, dx2);
        }
    }

    private void drawFlatMax(final Triangle t,
            final Point2D v,
            final float maxY,
            final float dx1,
            final float dx2) {

        float x1 = v.x();
        float x2 = x1;

        for (int y = (int) v.y(); y <= maxY; y++) {
            // round floats instead of floor?
            drawHLine(t, (int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatMin(final Triangle t,
            final Point2D v,
            final float minY,
            final float dx1,
            final float dx2) {

        float x1 = v.x();
        float x2 = x1;

        for (int y = (int) v.y(); y > minY; y--) {
            // round floats instead of floor?
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
                b = t.barycentrics(new Vector(x, y));
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

        final List<Point2D> vertices = sortedVertices(t);

        final Point2D v1 = vertices.get(0);
        final Point2D v2 = vertices.get(1);
        final Point2D v3 = vertices.get(2);

        final float x1 = v1.x();
        final float y1 = v1.y();

        final float x2 = v2.x();
        final float y2 = v2.y();

        final float x3 = v3.x();
        final float y3 = v3.y();

        if (Floats.equals(y2, y3)) {
            drawFlat(t, v1, v2, v3);
            return;
        }

        if (Floats.equals(y1, y2)) {
            drawFlat(t, v3, v1, v2);
            return;
        }

        final float x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final Point2D v4 = new Vector(x4, v2.y());

        // non strict equality?
        if (Floats.moreThan(x4, x2)) {
            drawFlat(t, v1, v2, v4);
            drawFlat(t, v3, v2, v4);
        } else {
            drawFlat(t, v1, v4, v2);
            drawFlat(t, v3, v4, v2);
        }
    }
}
