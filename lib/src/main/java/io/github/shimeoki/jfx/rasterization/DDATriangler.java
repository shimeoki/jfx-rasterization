package io.github.shimeoki.jfx.rasterization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.math.Floats;
import io.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentricser;
import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.geom.Vector2f;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**
 * A {@link Triangler Triangler} implementation using the digital differential
 * analyzer algorithm for drawing slopes and drawing pixels one by one.
 *
 * <p>
 * The algorithm separates the triangle into two (with flat side on max and/or
 * min Y level), and then draws the slopes pixel by pixel with the triangle
 * itself, using the scanline approach.
 * <p>
 * Uses floats for all calculations and converts the coordinates for
 * rasterization to integers at the last stage - drawing horizontal lines. All
 * floats at the convertion are floored (not rounded) for consistency.
 * <p>
 * Because of that, the triangles can be displaced to the start of the
 * coordinate plane, and the gaps can be seen between the triangles, if this
 * rasterization is used to draw triangulated polygons.
 * <p>
 * Sets pixels one by one with the
 * {@link PixelWriter#setColor(int, int, javafx.scene.paint.Color)} call (the
 * rasterization is not buffered). It's very slow, so this implementation is not
 * recommended for fast rendering.
 * <p>
 * Algorithm documentation: <a href=
 * "https://en.wikipedia.org/wiki/Digital_differential_analyzer_(graphics_algorithm)">Wikipedia</a>.
 * <p>
 * The implementation is heavily based on <a href=
 * "https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html">this
 * article</a>.
 *
 * @since 2.0.0
 */
public final class DDATriangler implements Triangler {

    private final PixelWriter writer;
    private TriangleFiller filler = new MonotoneTriangleFiller(HTMLColorf.BLACK);
    private Colorf color;

    private final TriangleBarycentricser barycentricser = new TriangleBarycentricser();
    private final TriangleBarycentrics barycentrics = barycentricser.barycentrics();
    private final List<Point2f> vertices = new ArrayList<>(3);
    private final Point2f point = new Vector2f(0, 0);

    private Point2f v1, v2, v3, v4 = new Vector2f(0, 0);
    private float v1x, v1y, v2x, v2y, v3x, v3y, v4x;

    private float x1, x2, dx1, dx2, x0, y0, limY;
    private int x, y;

    /**
     * Creates a new {@code DDATriangler} instance.
     *
     * <p>
     * Anchores itself to the passed {@link GraphicsContext}: contains the reference
     * to the {@link PixelWriter} from the context.
     * <p>
     * It's not a singleton class, so instances should be created and used
     * separately.
     *
     * @param ctx graphics context to use for the rasterization
     *
     * @since 2.0.0
     *
     * @see GraphicsContext
     */
    public DDATriangler(final GraphicsContext ctx) {
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

    private void update(final Triangle t) {
        vertices.clear();

        vertices.add(t.v1());
        vertices.add(t.v2());
        vertices.add(t.v3());

        vertices.sort(Comparator
                .comparing(Point2f::y)
                .thenComparing(Point2f::x));

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

    private void drawFlat(final Point2f p0, final Point2f p1, final Point2f p2) {
        x0 = p0.x();
        y0 = p0.y();

        dx1 = (p1.x() - x0) / (p1.y() - y0);
        dx2 = (p2.x() - x0) / (p2.y() - y0);

        limY = p1.y();
        if (Floats.moreThan(y0, limY)) {
            drawFlatAtMinY();
        } else {
            drawFlatAtMaxY();
        }
    }

    private void drawFlatAtMaxY() {
        x1 = x0;
        x2 = x1;

        for (y = (int) y0; y <= limY; y++) {
            drawHLine();

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatAtMinY() {
        x1 = x0;
        x2 = x1;

        for (y = (int) y0; y > limY; y--) {
            drawHLine();

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine() {
        point.setY(y);

        for (x = (int) x1; x <= x2; x++) {
            point.setX(x);

            barycentricser.calculate(point);

            if (!barycentrics.normalized()) {
                continue;
            }

            if (!barycentrics.inside()) {
                continue;
            }

            color = filler.color(barycentrics);

            if (color == null) {
                continue;
            }

            writer.setColor(x, y, color.jfxColor());
        }
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        barycentricser.setTriangle(t);
        update(t);

        if (Floats.equals(v2y, v3y)) {
            drawFlat(v1, v2, v3);
            return;
        }

        if (Floats.equals(v1y, v2y)) {
            drawFlat(v3, v1, v2);
            return;
        }

        v4x = v1x + ((v2y - v1y) / (v3y - v1y)) * (v3x - v1x);

        v4.setX(v4x);
        v4.setY(v2y);

        if (Floats.moreThan(v4x, v2x)) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }
    }
}
