package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.math.Floats;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;
import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector;

import javafx.scene.image.PixelWriter;

public class DDATriangler implements Triangler {

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    private List<FloatPoint2D> sortedVertices() {
        final List<FloatPoint2D> vertices = new ArrayList<>();

        vertices.add(triangle.v1());
        vertices.add(triangle.v2());
        vertices.add(triangle.v3());

        vertices.sort(Comparator
                .comparing(FloatPoint2D::y)
                .thenComparing(FloatPoint2D::x));

        return vertices;
    }

    private void drawFlat(
            final FloatPoint2D lone,
            final FloatPoint2D flat1,
            final FloatPoint2D flat2) {

        final float loneX = lone.x();
        final float loneY = lone.y();

        final float deltaXFlat1 = flat1.x() - loneX;
        final float deltaYFlat1 = flat1.y() - loneY;

        final float deltaXFlat2 = flat2.x() - loneX;
        final float deltaYFlat2 = flat2.y() - loneY;

        float dx1 = deltaXFlat1 / deltaYFlat1;
        float dx2 = deltaXFlat2 / deltaYFlat2;

        final float flatY = flat1.y();

        if (Floats.moreThan(loneY, flatY)) {
            drawFlatAtMinY(lone, flatY, dx1, dx2);
        } else {
            drawFlatAtMaxY(lone, flatY, dx1, dx2);
        }
    }

    private void drawFlatAtMaxY(
            final FloatPoint2D anchor,
            final float maxY,
            final float dx1,
            final float dx2) {

        float x1 = anchor.x();
        float x2 = x1;

        for (int y = (int) anchor.y(); y <= maxY; y++) {
            // round floats instead of floor?
            drawHLine((int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatAtMinY(
            final FloatPoint2D anchor,
            final float minY,
            final float dx1,
            final float dx2) {

        float x1 = anchor.x();
        float x2 = x1;

        for (int y = (int) anchor.y(); y > minY; y--) {
            // round floats instead of floor?
            drawHLine((int) x1, (int) x2, y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine(final int x1, final int x2, final int y) {
        for (int x = (int) x1; x <= x2; x++) {
            final TriangleBarycentrics barycentrics;
            try {
                barycentrics = triangle.barycentrics(new FloatVector(x, y));
            } catch (Exception e) {
                continue;
            }

            if (!barycentrics.inside()) {
                continue;
            }

            writer.setColor(x, y, colorer.get(barycentrics).jfxColor());
        }
    }

    private void cache(
            final PixelWriter w,
            final Triangle t,
            final TriangleColorer c) {

        writer = w;
        triangle = t;
        colorer = c;
    }

    private void uncache() {
        writer = null;
        triangle = null;
        colorer = null;
    }

    @Override
    public void draw(
            final PixelWriter w,
            final Triangle t,
            final TriangleColorer c) {

        // TODO: too many lines in this method

        Objects.requireNonNull(w);
        Objects.requireNonNull(t);
        Objects.requireNonNull(c);

        cache(w, t, c);

        // docs:
        // https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html

        final List<FloatPoint2D> vertices = sortedVertices();

        final FloatPoint2D v1 = vertices.get(0);
        final FloatPoint2D v2 = vertices.get(1);
        final FloatPoint2D v3 = vertices.get(2);

        final float x1 = v1.x();
        final float y1 = v1.y();

        final float x2 = v2.x();
        final float y2 = v2.y();

        final float x3 = v3.x();
        final float y3 = v3.y();

        if (Floats.equals(y2, y3)) {
            drawFlat(v1, v2, v3);
            return;
        }

        if (Floats.equals(y1, y2)) {
            drawFlat(v3, v1, v2);
            return;
        }

        final float x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final FloatPoint2D v4 = new FloatVector(x4, v2.y());

        // non strict equality?
        if (Floats.moreThan(x4, x2)) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }

        uncache();
    }
}
