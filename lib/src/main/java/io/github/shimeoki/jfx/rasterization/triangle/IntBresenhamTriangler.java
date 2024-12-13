package io.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.HTMLColorf;
import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.geom.Point2i;
import io.github.shimeoki.jfx.rasterization.geom.Vector2f;
import io.github.shimeoki.jfx.rasterization.geom.Vector2i;
import io.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentricser;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**
 * A {@link Triangler Triangler} implementation using the Bresenham
 * algorithm (on integers) for drawing slopes and drawing pixels one by one.
 *
 * <p>
 * The algorithm separates the triangle into two (with flat side on max and/or
 * min Y level), and then draws the slopes pixel by pixel with the triangle
 * itself, using the scanline approach.
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
 * @since 1.0.0
 */
public final class IntBresenhamTriangler implements Triangler {

    // miscellaneous

    private final PixelWriter writer;
    private TriangleFiller filler = new MonotoneTriangleFiller(HTMLColorf.BLACK);

    private final TriangleBarycentricser barycentricser = new TriangleBarycentricser();
    private final TriangleBarycentrics barycentrics = barycentricser.barycentrics();
    private final List<Point2i> vertices = new ArrayList<>(3);
    private final Point2f point = new Vector2f(0, 0);

    // vertices

    private Point2i v1;
    private int v1x;
    private int v1y;

    private Point2i v2;
    private int v2x;
    private int v2y;

    private Point2i v3;
    private int v3x;
    private int v3y;

    private Point2i v4 = new Vector2i(0, 0);
    private int v4x;

    // process

    private int tmp;

    private int x;
    private int i;

    private int x1;
    private int x2;

    private int dx1;
    private int dx2;

    private int y1;
    private int y2;

    private int dy1;
    private int dy2;

    private int sx1;
    private int sx2;
    private int sy;

    private boolean changed1;
    private boolean changed2;

    private int err1;
    private int err2;

    /**
     * Creates a new {@code IntBresenhamTriangler} instance.
     *
     * <p>
     * It's not a singleton class, so instances should be created and used
     * separately.
     *
     * @since 1.0.0
     */
    public IntBresenhamTriangler(final GraphicsContext ctx) {
        writer = Objects.requireNonNull(ctx).getPixelWriter();
    }

    @Override
    public TriangleFiller filler() {
        return filler;
    }

    @Override
    public void setFiller(final TriangleFiller f) {
        filler = Objects.requireNonNull(f);
    }

    private Point2i converted(final Point2f p) {
        return new Vector2i((int) p.x(), (int) p.y());
    }

    private void update(final Triangle t) {
        vertices.clear();

        vertices.add(converted(t.v1()));
        vertices.add(converted(t.v2()));
        vertices.add(converted(t.v3()));

        vertices.sort(Comparator
                .comparing(Point2i::y)
                .thenComparing(Point2i::x));

        v1 = vertices.get(0);
        v2 = vertices.get(1);
        v3 = vertices.get(2);

        v1x = v1.x();
        v1y = v1.y();

        v2x = v2.x();
        v2y = v2.y();

        v3x = v3.x();
        v3y = v3.y();
    }

    private void swap1() {
        if (dy1 <= dx1) {
            return;
        }

        tmp = dx1;
        dx1 = dy1;
        dy1 = tmp;

        changed1 = true;
    }

    private void swap2() {
        if (dy2 <= dx2) {
            return;
        }

        tmp = dx2;
        dx2 = dy2;
        dy2 = tmp;

        changed2 = true;
    }

    private void spendError1() {
        while (err1 >= 0) {
            if (changed1) {
                x1 += sx1;
            } else {
                y1 += sy;
            }

            err1 -= 2 * dx1;
        }
    }

    private void spendError2() {
        while (err2 >= 0) {
            if (changed2) {
                x2 += sx2;
            } else {
                y2 += sy;
            }

            err2 -= 2 * dx2;
        }
    }

    private void prepare(final Point2i p0, final Point2i p1, final Point2i p2) {
        x1 = p0.x();
        x2 = x1;

        y1 = p0.y();
        y2 = y1;

        changed1 = false;
        changed2 = false;

        dx1 = p1.x() - x1;
        dx2 = p2.x() - x1;

        sx1 = (int) Math.signum(dx1);
        sx2 = (int) Math.signum(dx2);

        dx1 = Math.abs(dx1);
        dx2 = Math.abs(dx2);

        dy1 = p1.y() - y1;
        dy2 = p2.y() - y1;

        sy = (int) Math.signum(dy1);

        dy1 = Math.abs(dy1);
        dy2 = Math.abs(dy2);

        swap1();
        swap2();

        err1 = 2 * dy1 - dx1;
        err2 = 2 * dy2 - dx2;
    }

    private void process(final Point2i p0, final Point2i p1, final Point2i p2) {
        prepare(p0, p1, p2);

        for (i = 0; i <= dx1; i++) {
            drawHLine();

            spendError1();

            if (changed1) {
                y1 += sy;
            } else {
                x1 += sx1;
            }

            err1 += 2 * dy1;

            while (y1 != y2) {
                spendError2();

                if (changed2) {
                    y2 += sy;
                } else {
                    x2 += sx2;
                }

                err2 += 2 * dy2;
            }
        }
    }

    private void drawHLine() {
        point.setY(y1);

        for (x = x1; x <= x2; x++) {
            point.setX(x);

            barycentricser.calculate(point);

            if (!barycentrics.normalized()) {
                continue;
            }

            if (!barycentrics.inside()) {
                continue;
            }

            writer.setColor(x, y1, filler.color(barycentrics).jfxColor());
        }
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        barycentricser.setTriangle(t);
        update(t);

        if (v2y == v3y) {
            process(v1, v2, v3);
            return;
        }

        if (v1y == v2y) {
            process(v3, v1, v2);
            return;
        }

        v4x = (int) (v1x + ((float) (v2y - v1y) / (float) (v3y - v1y)) * (v3x - v1x));

        v4.setX(v4x);
        v4.setY(v2y);

        if (v4x > v2x) {
            process(v1, v2, v4);
            process(v3, v2, v4);
        } else {
            process(v1, v4, v2);
            process(v3, v4, v2);
        }
    }
}
