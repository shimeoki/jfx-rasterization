package io.github.shimeoki.jfx.rasterization.triangle;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.Colorf;
import io.github.shimeoki.jfx.rasterization.geom.Pos2f;
import io.github.shimeoki.jfx.rasterization.geom.Vector2f;
import io.github.shimeoki.jfx.rasterization.math.Floats;
import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class BufferedDDATriangler implements Triangler {

    private static final PixelFormat<IntBuffer> PIXEL_FORMAT = PixelFormat.getIntArgbInstance();

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    private Pos2f v1;
    private Pos2f v2;
    private Pos2f v3;

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    private float x3;
    private float y3;

    private int[] buffer;

    private void cacheVertices() {
        final List<Pos2f> vertices = new ArrayList<>();

        vertices.add(triangle.v1());
        vertices.add(triangle.v2());
        vertices.add(triangle.v3());

        vertices.sort(Comparator
                .comparing(Pos2f::y)
                .thenComparing(Pos2f::x));

        v1 = vertices.get(0);
        v2 = vertices.get(1);
        v3 = vertices.get(2);

        x1 = v1.x();
        y1 = v1.y();

        x2 = v2.x();
        y2 = v2.y();

        x3 = v3.x();
        y3 = v3.y();
    }

    private void cache(
            final PixelWriter w,
            final Triangle t,
            final TriangleColorer c) {

        writer = w;
        triangle = t;
        colorer = c;

        cacheVertices();
    }

    private void uncache() {
        writer = null;
        triangle = null;
        colorer = null;

        v1 = null;
        v2 = null;
        v3 = null;

        x1 = 0;
        y1 = 0;

        x2 = 0;
        y2 = 0;

        x3 = 0;
        y3 = 0;
    }

    private void drawFlat(final Pos2f v0, final Pos2f v1, final Pos2f v2) {
        final float x0 = v0.x();
        final float y0 = v0.y();

        final float y1 = v1.y();
        final float dy = y1 - y0;

        final float dx1 = (v1.x() - x0) / dy;
        final float dx2 = (v2.x() - x0) / dy;

        if (Floats.moreThan(y0, y1)) {
            drawFlatAtMinY(v0, y1, dx1, dx2);
        } else {
            drawFlatAtMaxY(v0, y1, dx1, dx2);
        }
    }

    private void drawFlatAtMaxY(
            final Pos2f v0,
            final float yMax,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = (int) v0.y(); y <= yMax; y++) {
            drawHLine((int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatAtMinY(
            final Pos2f v0,
            final float yMin,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = (int) v0.y(); y > yMin; y--) {
            drawHLine((int) x1, (int) x2, y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine(final int x1, final int x2, final int y) {
        final List<Integer> pixels = new ArrayList<>(x2 - x1 + 1);

        int offset = 0;
        boolean start = true;

        Pos2f point;
        TriangleBarycentrics barycentrics;
        int argb;

        for (int x = x1; x <= x2; x++) {
            point = new Vector2f(x, y);

            try {
                barycentrics = triangle.barycentrics(point);
                if (!barycentrics.inside()) {
                    throw new IllegalArgumentException("point is not inside");
                }
            } catch (final IllegalArgumentException e) {
                if (start) {
                    offset++;
                    continue;
                } else {
                    break;
                }
            }

            start = false;

            argb = argb(colorer.get(barycentrics));

            pixels.add(argb);
        }

        buffer = pixels.stream().mapToInt(i -> i).toArray();

        drawBuffer(x1 + offset, y);
    }

    private int argb(final Colorf c) {
        final int alpha = (int) (c.alpha() * 255);
        final int red = (int) (c.red() * 255);
        final int green = (int) (c.green() * 255);
        final int blue = (int) (c.blue() * 255);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    private void drawBuffer(final int x, final int y) {
        final int width = buffer.length;

        writer.setPixels(
                x,
                y,
                width,
                1,
                PIXEL_FORMAT,
                buffer,
                0,
                width);
    }

    private boolean drawOnlyMax() {
        if (Floats.compare(y2, y3) != 0) {
            return false;
        }

        drawFlat(v1, v2, v3);
        return true;
    }

    private boolean drawOnlyMin() {
        if (Floats.compare(y1, y2) != 0) {
            return false;
        }

        drawFlat(v3, v1, v2);
        return true;
    }

    private void drawSplit() {
        final float x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final Pos2f v4 = new Vector2f(x4, y2);

        if (Floats.moreThan(x4, x2)) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }
    }

    @Override
    public void draw(
            final GraphicsContext ctx,
            final Triangle t,
            final TriangleColorer c) {

        Objects.requireNonNull(ctx);
        Objects.requireNonNull(t);
        Objects.requireNonNull(c);

        cache(ctx.getPixelWriter(), t, c);

        boolean onlyMax = false;
        boolean onlyMin = false;

        onlyMax = drawOnlyMax();

        if (!onlyMax) {
            onlyMin = drawOnlyMin();
        }

        if (!onlyMax && !onlyMin) {
            drawSplit();
        }

        uncache();
    }
}
