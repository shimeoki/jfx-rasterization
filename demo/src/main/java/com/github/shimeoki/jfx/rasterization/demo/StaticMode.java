package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.color.HTMLColorf;
import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Vector2f;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.DynamicTriangleGradient;
import com.github.shimeoki.jfx.rasterization.triangle.color.StaticGradientTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.StaticTriangle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class StaticMode {

    @FXML
    private AnchorPane root;

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
        private final PixelWriter writer;

        private final Triangler r;
        private final TriangleColorer colorer;

        // 6 numbers from 0 to 1
        // 2 coordinates for 3 points
        // represent % of length in the canvas
        private final float[] pos = new float[6];

        private final Random rnd = new Random();

        Tile(final Canvas canvas) {
            Objects.requireNonNull(canvas);

            this.c = canvas;
            this.ctx = c.getGraphicsContext2D();
            this.writer = ctx.getPixelWriter();

            this.r = new DDATriangler();
            this.colorer = new StaticGradientTriangleColorer(
                    new DynamicTriangleGradient(
                            HTMLColorf.RED,
                            HTMLColorf.WHITE,
                            HTMLColorf.BLACK));

            generate();
        }

        private Pos2f getPoint1() {
            final float x = (float) c.getWidth() * pos[0];
            final float y = (float) c.getHeight() * pos[1];
            return new Vector2f(x, y);
        }

        private Pos2f getPoint2() {
            final float x = (float) c.getWidth() * pos[2];
            final float y = (float) c.getHeight() * pos[3];
            return new Vector2f(x, y);
        }

        private Pos2f getPoint3() {
            final float x = (float) c.getWidth() * pos[4];
            final float y = (float) c.getHeight() * pos[5];
            return new Vector2f(x, y);
        }

        void generate() {
            for (int i = 0; i < pos.length; i++) {
                pos[i] = rnd.nextFloat();
            }

            draw();
        }

        void clear() {
            ctx.clearRect(0, 0, c.getWidth(), c.getHeight());
        }

        void draw() {
            clear();

            final Pos2f p1 = getPoint1();
            final Pos2f p2 = getPoint2();
            final Pos2f p3 = getPoint3();

            r.draw(writer, new StaticTriangle(p1, p2, p3), colorer);
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

    public AnchorPane root() {
        return root;
    }
}
