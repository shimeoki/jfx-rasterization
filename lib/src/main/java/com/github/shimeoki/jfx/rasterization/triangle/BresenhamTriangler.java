package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector;
import com.github.shimeoki.jfx.rasterization.geom.IntPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.IntVector;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.image.PixelWriter;

public class BresenhamTriangler implements Triangler {

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    private IntPoint2D converted(final FloatPoint2D p) {
        return new IntVector((int) p.x(), (int) p.y());
    }

    private List<IntPoint2D> sortedVertices() {
        final List<IntPoint2D> vertices = new ArrayList<>();

        vertices.add(converted(triangle.v1()));
        vertices.add(converted(triangle.v2()));
        vertices.add(converted(triangle.v3()));

        vertices.sort(Comparator
                .comparing(IntPoint2D::y)
                .thenComparing(IntPoint2D::x));

        return vertices;
    }

    private void drawFlat(
            final IntPoint2D lone,
            final IntPoint2D flat1,
            final IntPoint2D flat2) {

        // TODO: refactor

        int x1 = lone.x();
        int y1 = lone.y();

        int x2 = x1;
        int y2 = y1;

        boolean changed1 = false;
        boolean changed2 = false;

        int dx1 = flat1.x() - x1;
        int dx2 = flat2.x() - x1;

        final int signx1 = (int) Math.signum(dx1);
        final int signx2 = (int) Math.signum(dx2);

        dx1 = Math.abs(dx1);
        dx2 = Math.abs(dx2);

        int dy1 = flat1.y() - y1;
        int dy2 = flat2.y() - y1;

        final int signy = (int) Math.signum(dy1);

        dy1 = Math.abs(dy1);
        dy2 = Math.abs(dy2);

        if (dy1 > dx1) {
            final int tmp = dx1;
            dx1 = dy1;
            dy1 = tmp;

            changed1 = true;
        }

        if (dy2 > dx2) {
            final int tmp = dx2;
            dx2 = dy2;
            dy2 = tmp;

            changed2 = true;
        }

        int err1 = 2 * dy1 - dx1;
        int err2 = 2 * dy2 - dx2;

        for (int i = 0; i <= dx1; i++) {
            drawHLine(x1, x2, y1);

            while (err1 >= 0) {
                if (changed1) {
                    x1 += signx1;
                } else {
                    y1 += signy;
                }

                err1 -= 2 * dx1;
            }

            if (changed1) {
                y1 += signy;
            } else {
                x1 += signx1;
            }

            err1 += 2 * dy1;

            while (y1 != y2) {
                while (err2 >= 0) {
                    if (changed2) {
                        x2 += signx2;
                    } else {
                        y2 += signy;
                    }

                    err2 -= 2 * dx2;
                }

                if (changed2) {
                    y2 += signy;
                } else {
                    x2 += signx2;
                }

                err2 += 2 * dy2;
            }
        }
    }

    private void drawHLine(final int x1, final int x2, final int y) {
        for (int x = x1; x <= x2; x++) {
            final TriangleBarycentrics barycentrics;
            try {
                barycentrics = triangle.barycentrics(new FloatVector(x, y));
            } catch (final Exception e) {
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

        final List<IntPoint2D> vertices = sortedVertices();

        final IntPoint2D v1 = vertices.get(0);
        final IntPoint2D v2 = vertices.get(1);
        final IntPoint2D v3 = vertices.get(2);

        final int x1 = v1.x();
        final int y1 = v1.y();

        final int x2 = v2.x();
        final int y2 = v2.y();

        final int x3 = v3.x();
        final int y3 = v3.y();

        if (y2 == y3) {
            drawFlat(v1, v2, v3);
            return;
        }

        if (y1 == y2) {
            drawFlat(v3, v1, v2);
            return;
        }

        final int x4 = (int) (x1 + ((float) (y2 - y1) / (float) (y3 - y1)) * (x3 - x1));
        final IntPoint2D v4 = new IntVector(x4, v2.y());

        // non strict equality?
        if (x4 > x2) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }

        uncache();
    }
}
