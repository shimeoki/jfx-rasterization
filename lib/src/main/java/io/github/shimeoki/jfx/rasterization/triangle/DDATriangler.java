package io.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.math.Floats;
import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;
import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
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
 * coordinate plane, and gaps can be seen between the triangles, if this
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
 * @since 1.0.0
 */
public final class DDATriangler implements Triangler {

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    /**
     * Creates a new {@code DDATriangler} instance.
     *
     * <p>
     * It's not a singleton class, so instances should be created and used
     * separately.
     *
     * @since 1.0.0
     */
    public DDATriangler() {
    }

    private List<Pos2f> sortedVertices() {
        final List<Pos2f> vertices = new ArrayList<>();

        vertices.add(triangle.v1());
        vertices.add(triangle.v2());
        vertices.add(triangle.v3());

        vertices.sort(Comparator
                .comparing(Pos2f::y)
                .thenComparing(Pos2f::x));

        return vertices;
    }

    private void drawFlat(
            final Pos2f lone,
            final Pos2f flat1,
            final Pos2f flat2) {

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
            final Pos2f anchor,
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
            final Pos2f anchor,
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
        for (int x = x1; x <= x2; x++) {
            final TriangleBarycentrics barycentrics;
            try {
                barycentrics = triangle.barycentrics(new Vector2f(x, y));
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
            final GraphicsContext ctx,
            final Triangle triangle,
            final TriangleColorer colorer) {

        // TODO: too many lines in this method

        Objects.requireNonNull(ctx);
        Objects.requireNonNull(triangle);
        Objects.requireNonNull(colorer);

        cache(ctx.getPixelWriter(), triangle, colorer);

        final List<Pos2f> vertices = sortedVertices();

        final Pos2f v1 = vertices.get(0);
        final Pos2f v2 = vertices.get(1);
        final Pos2f v3 = vertices.get(2);

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
        final Pos2f v4 = new Vector2f(x4, v2.y());

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
