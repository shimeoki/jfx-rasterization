package com.github.shimeoki.jfx.rasterization.triangle;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Pos2i;
import com.github.shimeoki.jfx.rasterization.geom.Vector2f;
import com.github.shimeoki.jfx.rasterization.geom.Vector2i;
import com.github.shimeoki.jfx.rasterization.math.Ints;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

public class BufferedDDATriangler implements Triangler {

    private static final PixelFormat<IntBuffer> PIXEL_FORMAT = PixelFormat.getIntArgbInstance();

    private PixelWriter writer = null;
    private Triangle triangle = null;
    private TriangleColorer colorer = null;

    private Pos2i v1;
    private Pos2i v2;
    private Pos2i v3;

    private int xMin;
    private int xMax;

    private int yMin;
    private int yMax;

    private int[] buffer;

    private Pos2i converted(final Pos2f v) {
        return new Vector2i((int) v.x(), (int) v.y());
    }

    private void cacheVertices() {
        final List<Pos2i> vertices = new ArrayList<>();

        vertices.add(converted(triangle.v1()));
        vertices.add(converted(triangle.v2()));
        vertices.add(converted(triangle.v3()));

        vertices.sort(Comparator
                .comparing(Pos2i::y)
                .thenComparing(Pos2i::x));

        v1 = vertices.get(0);
        v2 = vertices.get(1);
        v3 = vertices.get(2);
    }

    private void cacheBounds() {
        yMin = v1.y();
        yMax = v3.y();

        final int x1 = v1.x();
        final int x2 = v2.x();
        final int x3 = v3.x();

        xMin = Ints.min3(x1, x2, x3);
        xMax = Ints.max3(x1, x2, x3);
    }

    private void makeBuffer(final List<Integer> lst) {
        buffer = lst.stream().mapToInt(i -> i).toArray();
    }

    private void cache(
            final PixelWriter w, final Triangle t, final TriangleColorer c) {

        writer = w;
        triangle = t;
        colorer = c;

        cacheVertices();
        cacheBounds();
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

    private void drawFlat(
            final Pos2i v0,
            final Pos2i v1,
            final Pos2i v2) {

        final int x0 = v0.x();
        final int y0 = v0.y();

        final float dx1 = v1.x() - x0;
        final float dy1 = v1.y() - y0;

        final float dx2 = v2.x() - x0;
        final float dy2 = v2.y() - y0;

        final float d1 = dx1 / dy1;
        final float d2 = dx2 / dy2;

        final int yFlat = v1.y();

        if (y0 >= yFlat) {
            drawFlatAtMinY(v0, yFlat, d1, d2);
        } else {
            drawFlatAtMaxY(v0, yFlat, d1, d2);
        }
    }

    private void drawFlatAtMaxY(
            final Pos2i v0,
            final int y0,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = v0.y(); y <= y0; y++) {
            drawHLine((int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatAtMinY(
            final Pos2i v0,
            final int y0,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = v0.y(); y > y0; y--) {
            drawHLine((int) x1, (int) x2, y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine(
            final int x1,
            final int x2,
            final int y) {

        final List<Integer> pixels = new ArrayList<>();

        for (int x = x1; x <= x2; x++) {
            final TriangleBarycentrics barycentrics;
            try {
                barycentrics = triangle.barycentrics(new Vector2f(x, y));
            } catch (final Exception e) {
                continue;
            }

            final int argb = colorer.get(barycentrics).argb();
            pixels.add(argb);
        }

        makeBuffer(pixels);
        drawBuffer(x1, y);
    }

    private boolean bufferOnlyMax() {
        if (v2.y() != v3.y()) {
            return false;
        }

        drawFlat(v1, v2, v3);
        return true;
    }

    private boolean bufferOnlyMin() {
        if (v1.y() != v2.y()) {
            return false;
        }

        drawFlat(v3, v1, v2);
        return true;
    }

    private void bufferSplit() {
        final float x1 = v1.x();
        final float y1 = v1.y();

        final float x2 = v2.x();
        final float y2 = v2.y();

        final float x3 = v3.x();
        final float y3 = v3.y();

        final float x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final Pos2i v4 = new Vector2i((int) x4, (int) y2);

        if (x4 >= x2) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }
    }

    @Override
    public void draw(
            final PixelWriter w,
            final Triangle t,
            final TriangleColorer c) {

        Objects.requireNonNull(w);
        Objects.requireNonNull(t);
        Objects.requireNonNull(c);

        cache(w, t, c);

        boolean onlyMax = false;
        boolean onlyMin = false;

        onlyMax = bufferOnlyMax();

        if (!onlyMax) {
            onlyMin = bufferOnlyMin();
        }

        if (!onlyMax && !onlyMin) {
            bufferSplit();
        }
    }
}
