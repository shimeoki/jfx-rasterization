package com.github.shimeoki.jfx.rasterization.triangle;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Pos2i;
import com.github.shimeoki.jfx.rasterization.geom.Vector2f;
import com.github.shimeoki.jfx.rasterization.geom.Vector2i;
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

    private int[][] buffer;

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

    private void cacheBuffer() {
        buffer = new int[v3.y() - v1.y() + 1][];
    }

    private void cache(
            final PixelWriter w, final Triangle t, final TriangleColorer c) {

        writer = w;
        triangle = t;
        colorer = c;

        cacheVertices();
        cacheBuffer();
    }

    private void uncache() {
        writer = null;
        triangle = null;
        colorer = null;

        v1 = null;
        v2 = null;
        v3 = null;

        buffer = null;
    }

    private void drawBuffer() {
        int y = v1.y();

        int[] line;
        int width;
        for (int i = 0; i < buffer.length; i++, y++) {
            line = buffer[i];
            if (line == null) {
                return;
            }

            width = line.length - 1;

            writer.setPixels(
                    line[0],
                    y,
                    width,
                    1,
                    PIXEL_FORMAT,
                    line,
                    1,
                    width);
        }
    }

    private void bufferFlat(
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
            bufferFlatAtMinY(v0, yFlat, d1, d2);
        } else {
            bufferFlatAtMaxY(v0, yFlat, d1, d2);
        }
    }

    private void bufferFlatAtMaxY(
            final Pos2i v0,
            final int y0,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = v0.y(); y <= y0; y++) {
            bufferHLine((int) x1, (int) x2, y);

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void bufferFlatAtMinY(
            final Pos2i v0,
            final int y0,
            final float dx1,
            final float dx2) {

        float x1 = v0.x();
        float x2 = x1;

        for (int y = v0.y(); y > y0; y--) {
            bufferHLine((int) x1, (int) x2, y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void bufferHLine(
            final int x1,
            final int x2,
            final int y) {

        final List<Integer> line = new ArrayList<>();
        line.add(x1);

        for (int x = x1; x <= x2; x++) {
            final TriangleBarycentrics barycentrics;
            try {
                barycentrics = triangle.barycentrics(new Vector2f(x, y));
            } catch (final Exception e) {
                continue;
            }

            final int argb = colorer.get(barycentrics).argb();
            line.add(argb);
        }

        buffer[y - v1.y()] = line.stream().mapToInt(i -> i).toArray();
    }

    private boolean bufferOnlyMax() {
        if (v2.y() != v3.y()) {
            return false;
        }

        bufferFlat(v1, v2, v3);
        return true;
    }

    private boolean bufferOnlyMin() {
        if (v1.y() != v2.y()) {
            return false;
        }

        bufferFlat(v3, v1, v2);
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
            bufferFlat(v1, v2, v4);
            bufferFlat(v3, v2, v4);
        } else {
            bufferFlat(v1, v4, v2);
            bufferFlat(v3, v4, v2);
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

        drawBuffer();

        uncache();
    }
}
