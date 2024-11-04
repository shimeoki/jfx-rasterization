package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.color.HTMLColors;
import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector;
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
import javafx.scene.control.RadioButton;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane modePane;

    @FXML
    private AnchorPane staticPane;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button tilesBtn;

    private final List<Tile> tiles = new LinkedList<>();

    @FXML
    private RadioButton staticModeBtn;

    @FXML
    private RadioButton dynamicModeBtn;

    @FXML
    private RadioButton interactiveModeBtn;

    @FXML
    private InteractiveMode interactiveModeController;

    @FXML
    private DynamicMode dynamicModeController;

    @FXML
    private void initialize() {
        initTilePane();
        initTilesBtn();

        initTgBtns();
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
                            HTMLColors.RED,
                            HTMLColors.WHITE,
                            HTMLColors.BLACK));

            generate();
        }

        private FloatPoint2D getPoint1() {
            final float x = (float) c.getWidth() * pos[0];
            final float y = (float) c.getHeight() * pos[1];
            return new FloatVector(x, y);
        }

        private FloatPoint2D getPoint2() {
            final float x = (float) c.getWidth() * pos[2];
            final float y = (float) c.getHeight() * pos[3];
            return new FloatVector(x, y);
        }

        private FloatPoint2D getPoint3() {
            final float x = (float) c.getWidth() * pos[4];
            final float y = (float) c.getHeight() * pos[5];
            return new FloatVector(x, y);
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

            final FloatPoint2D p1 = getPoint1();
            final FloatPoint2D p2 = getPoint2();
            final FloatPoint2D p3 = getPoint3();

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

    private void initTgBtns() {
        final AnchorPane interactiveMode = interactiveModeController.root();
        final AnchorPane dynamicMode = dynamicModeController.root();

        staticModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(staticPane);
        });

        interactiveModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(interactiveMode);
        });

        dynamicModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(dynamicMode);
        });
    }
}
