package com.github.shimeoki.jfx_rasterization.app;

import java.util.Objects;
import java.util.Random;

import com.github.shimeoki.jfx_rasterization.lib.TriangleColor;
import com.github.shimeoki.jfx_rasterization.lib.TriangleRasterizer;
import com.github.shimeoki.jfx_rasterization.lib.rasterizers.StandardTriangleRasterizer;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class RasterizationController {

    @FXML
    private AnchorPane root;

    @FXML
    private TilePane tiles;

    @FXML
    private void initialize() {
        initTiles();
    }

    class Tile {

        private final Canvas c;
        private final GraphicsContext ctx;

        private final TriangleRasterizer r;

        // 6 numbers from 0 to 1
        // 2 coordinates for 3 points
        // represent % of length in the canvas
        private final double[] pos = new double[6];

        private final Random rnd = new Random();

        Tile(final Canvas canvas, final TriangleColor color) {
            Objects.requireNonNull(canvas);
            Objects.requireNonNull(color);

            this.c = canvas;
            this.ctx = c.getGraphicsContext2D();

            this.r = new StandardTriangleRasterizer();
            r.setColor(color);
            r.setCtx(ctx);

            generate();
        }

        private Point2D getPoint1() {
            final double x = c.getWidth() * pos[0];
            final double y = c.getHeight() * pos[1];
            return new Point2D(x, y);
        }

        private Point2D getPoint2() {
            final double x = c.getWidth() * pos[2];
            final double y = c.getHeight() * pos[3];
            return new Point2D(x, y);
        }

        private Point2D getPoint3() {
            final double x = c.getWidth() * pos[4];
            final double y = c.getHeight() * pos[5];
            return new Point2D(x, y);
        }

        void generate() {
            for (int i = 0; i < pos.length; i++) {
                pos[i] = rnd.nextDouble();
            }
        }

        void clear() {
            ctx.clearRect(0, 0, c.getWidth(), c.getHeight());
        }

        void draw() {
            clear();

            final Point2D p1 = getPoint1();
            final Point2D p2 = getPoint2();
            final Point2D p3 = getPoint3();

            r.draw(p1, p2, p3);
        }
    }

    private void initTiles() {
        tiles.setPrefRows(3);
        tiles.setPrefColumns(3);
    }
}
