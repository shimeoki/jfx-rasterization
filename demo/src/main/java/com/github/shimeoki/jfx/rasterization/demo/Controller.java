package com.github.shimeoki.jfx.rasterization.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.color.HTMLColors;
import com.github.shimeoki.jfx.rasterization.geom.Point2D;
import com.github.shimeoki.jfx.rasterization.geom.Vector;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.DefaultTriangleGradient;
import com.github.shimeoki.jfx.rasterization.triangle.color.GradientTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.StaticTriangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private AnchorPane interactivePane;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button tilesBtn;

    private final List<Tile> tiles = new LinkedList<>();

    @FXML
    private Canvas interactiveCanvas;

    @FXML
    private Pane interactiveCanvasPane;

    private Triangler interactiveTriangler;

    @FXML
    private Button interactiveClearBtn;

    private final List<Point2D> interactivePoints = new ArrayList<>();

    @FXML
    private RadioButton staticModeBtn;

    @FXML
    private RadioButton dynamicModeBtn;

    @FXML
    private RadioButton interactiveModeBtn;

    @FXML
    private void initialize() {
        initTilePane();
        initTilesBtn();

        initInteractiveCanvas();
        initInteractiveTriangler();
        initInteractiveClearBtn();

        initTgBtns();
    }

    class Tile {

        private final Canvas c;
        private final GraphicsContext ctx;

        private final Triangler r;

        // 6 numbers from 0 to 1
        // 2 coordinates for 3 points
        // represent % of length in the canvas
        private final float[] pos = new float[6];

        private final Random rnd = new Random();

        Tile(final Canvas canvas) {
            Objects.requireNonNull(canvas);

            this.c = canvas;
            this.ctx = c.getGraphicsContext2D();

            this.r = new DDATriangler(
                    ctx.getPixelWriter(),
                    new GradientTriangleColorer(
                            new DefaultTriangleGradient(
                                    HTMLColors.RED,
                                    HTMLColors.WHITE,
                                    HTMLColors.BLACK)));

            generate();
        }

        private Point2D getPoint1() {
            final float x = (float) c.getWidth() * pos[0];
            final float y = (float) c.getHeight() * pos[1];
            return new Vector(x, y);
        }

        private Point2D getPoint2() {
            final float x = (float) c.getWidth() * pos[2];
            final float y = (float) c.getHeight() * pos[3];
            return new Vector(x, y);
        }

        private Point2D getPoint3() {
            final float x = (float) c.getWidth() * pos[4];
            final float y = (float) c.getHeight() * pos[5];
            return new Vector(x, y);
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

            final Point2D p1 = getPoint1();
            final Point2D p2 = getPoint2();
            final Point2D p3 = getPoint3();

            r.draw(new StaticTriangle(p1, p2, p3));
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

    private void initInteractiveClearBtn() {
        final GraphicsContext ctx = interactiveCanvas.getGraphicsContext2D();

        interactiveClearBtn.setOnAction(e -> {
            interactivePoints.clear();
            ctx.clearRect(
                    0,
                    0,
                    interactiveCanvas.getWidth(),
                    interactiveCanvas.getHeight());
        });
    }

    private void initInteractiveTriangler() {
        interactiveTriangler = new DDATriangler(
                interactiveCanvas.getGraphicsContext2D().getPixelWriter(),
                new GradientTriangleColorer(
                        new DefaultTriangleGradient(
                                HTMLColors.AQUA,
                                HTMLColors.FUCHSIA,
                                HTMLColors.LIME)));
    }

    private void interactiveDraw() {
        final Triangle t = new StaticTriangle(
                interactivePoints.get(0),
                interactivePoints.get(1),
                interactivePoints.get(2));

        interactiveTriangler.draw(t);
    }

    private void initInteractiveCanvas() {
        interactiveCanvasPane.widthProperty()
                .addListener((ov, oldValue, newValue) -> {
                    interactiveCanvas.setWidth(newValue.doubleValue());
                });
        interactiveCanvasPane.heightProperty()
                .addListener((ov, oldValue, newValue) -> {
                    interactiveCanvas.setHeight(newValue.doubleValue());
                });

        interactiveCanvas.setOnMousePressed(e -> {
            final Point2D p = new Vector((float) e.getX(), (float) e.getY());
            interactivePoints.add(p);

            if (interactivePoints.size() == 3) {
                interactiveDraw();
                interactivePoints.clear();
            }
        });
    }

    private void initTgBtns() {
        staticModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(staticPane);
        });

        interactiveModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(interactivePane);
        });
    }
}
