package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Vector2f;
import com.github.shimeoki.jfx.rasterization.geom.Pos2i;
import com.github.shimeoki.jfx.rasterization.geom.Vector2i;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**
 * A {@link Triangler Triangler} implementation using the Bresenham
 * algorithm (on integers) for drawing slopes and drawing pixels one by one.
 *
 * <p>
 * Uses integers for all internal calculations. However, to make this work, all
 * triangle vertices are converted to their integer counterparts at the
 * inizialization phase. For the conversion, all coordinates are floored.
 * <p>
 * And, as an exception to the said above, barycentric coordinates for the color
 * fill are operating on floats.
 * <p>
 * Sets pixels one by one with the
 * {@link PixelWriter#setColor(int, int, javafx.scene.paint.Color)} call (the
 * rasterization is not buffered). It's very slow, so this implementation is not
 * recommended for fast rendering.
 * <p>
 * Algorithm documentation: <a href=
 * "https://en.wikipedia.org/wiki/Bresenham's_line_algorithm">Wikipedia</a>.
 * <p>
 * The implementation is heavily based on <a href=
 * "https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html">this
 * article</a>.
 *
 * @author shimeoki
 * @since 1.0.0
 */
public final class IntBresenhamTriangler implements Triangler {

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    private Pos2i converted(final Pos2f p) {
        return new Vector2i((int) p.x(), (int) p.y());
    }

    private List<Pos2i> sortedVertices() {
        final List<Pos2i> vertices = new ArrayList<>();

        vertices.add(converted(triangle.v1()));
        vertices.add(converted(triangle.v2()));
        vertices.add(converted(triangle.v3()));

        vertices.sort(Comparator
                .comparing(Pos2i::y)
                .thenComparing(Pos2i::x));

        return vertices;
    }

    private void drawFlat(
            final Pos2i lone,
            final Pos2i flat1,
            final Pos2i flat2) {

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
                barycentrics = triangle.barycentrics(new Vector2f(x, y));
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
            final GraphicsContext ctx,
            final Triangle triangle,
            final TriangleColorer colorer) {

        // TODO: too many lines in this method

        Objects.requireNonNull(ctx);
        Objects.requireNonNull(triangle);
        Objects.requireNonNull(colorer);

        cache(ctx.getPixelWriter(), triangle, colorer);

        final List<Pos2i> vertices = sortedVertices();

        final Pos2i v1 = vertices.get(0);
        final Pos2i v2 = vertices.get(1);
        final Pos2i v3 = vertices.get(2);

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
        final Pos2i v4 = new Vector2i(x4, v2.y());

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
