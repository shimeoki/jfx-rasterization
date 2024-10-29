package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.triangle.GradientTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane modePane;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button tilesBtn;

    private final List<Tile> tiles = new LinkedList<>();

    @FXML
    private void initialize() {
        initTilePane();
        initTilesBtn();
    }

    class Tile {

        private final Canvas c;
        private final GraphicsContext ctx;

        private final Triangler r;

        // 6 numbers from 0 to 1
        // 2 coordinates for 3 points
        // represent % of length in the canvas
        private final double[] pos = new double[6];

        private final Random rnd = new Random();

        Tile(final Canvas canvas) {
            Objects.requireNonNull(canvas);

            this.c = canvas;
            this.ctx = c.getGraphicsContext2D();

            this.r = new DDATriangler();
            r.setColorer(new GradientTriangleColorer());
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

            draw();
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

    private void initTilePane() {
        tilePane.setPrefRows(3);
        tilePane.setPrefColumns(3);

        initTiles();
    }

    private void initTiles() {
        final ObservableList<Node> children = tilePane.getChildren();

        final int rows = tilePane.getPrefRows();
        final int cols = tilePane.getPrefColumns();

        for (int i = 0; i < rows * cols; i++) {
            final Canvas c = new Canvas(100, 100);
            children.add(c);

            final Tile t = new Tile(c);
            tiles.add(t);
        }
    }

    private void initTilesBtn() {
        tilesBtn.setOnAction(e -> generateTiles());
    }

    private void drawTiles() {
        for (final Tile t : tiles) {
            t.draw();
        }
    }

    private void generateTiles() {
        for (final Tile t : tiles) {
            t.generate();
        }
    }
}
