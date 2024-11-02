package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.color.HTMLColors;
import com.github.shimeoki.jfx.rasterization.geom.Vector;
import com.github.shimeoki.jfx.rasterization.geom.Vector2D;
import com.github.shimeoki.jfx.rasterization.test.TimeUnit;
import com.github.shimeoki.jfx.rasterization.test.Timekeeper;
import com.github.shimeoki.jfx.rasterization.test.Timer;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.DynamicTriangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DynamicMode {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane canvasPane;

    @FXML
    private Canvas canvas;

    @FXML
    private ListView<KeepedTriangle> trianglesListView;

    @FXML
    private Button addBtn;

    @FXML
    private Button clearBtn;

    private final float velocity = 0.01f;

    private final Random rnd = new Random();

    private final List<MovingVector> vertices = new LinkedList<>();

    private final List<KeepedTriangle> triangles = new LinkedList<>();

    private final ObservableList<KeepedTriangle> trianglesList = FXCollections.observableArrayList();

    private Triangler triangler;

    class MovingVector {

        final Vector2D v;
        float angle;

        MovingVector(final Vector2D v, final float angle) {
            this.v = v;
            this.angle = angle;
        }

        void move(final float deltaTime) {
            final float distance = velocity * deltaTime;

            final float dx = distance * (float) Math.cos(angle);
            final float dy = distance * (float) Math.sin(angle);

            final float x = v.x() + dx;
            final float y = v.y() + dy;

            final float canvasW = (float) canvas.getWidth();
            final float canvasH = (float) canvas.getHeight();

            final float remX = canvasW - x;
            final float remY = canvasH - y;

            boolean outX = false;
            boolean outY = false;

            if (x < 0) {
                v.setX(-x);
                outX = true;
            }

            if (y < 0) {
                v.setY(-y);
                outY = true;
            }

            if (remX < 0) {
                v.setX(canvasW + remX);
                outX = true;
            }

            if (remY < 0) {
                v.setY(canvasH + remY);
                outY = true;
            }

            if (outX) {
                angle = 3.14f - angle;
            }

            if (outY) {
                angle = 2 * 3.14f - angle;
            }

            if (!outX) {
                v.setX(x);
            }

            if (!outY) {
                v.setY(y);
            }
        }
    }

    class KeepedTriangle {

        final Triangle t;
        final Timekeeper keeper;

        KeepedTriangle(final Triangle t) {
            this.t = t;

            keeper = new Timer();
            keeper.setUnit(TimeUnit.MILLISECOND);
        }

        @Override
        public String toString() {
            return String.format("last: %.2fms, avg: %.2fms",
                    keeper.lastTrack(),
                    keeper.avg());
        }
    }

    @FXML
    private void initialize() {
        initTriangler();
        initCanvas();
        initTrianglesListView();
        initAddBtn();
        initClearBtn();

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        final int frameTime = 15;
        final float deltaTime = 1000f / (float) frameTime;

        KeyFrame frame = new KeyFrame(Duration.millis(frameTime), e -> {
            draw();
            move(deltaTime);
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void initTrianglesListView() {
        trianglesListView.setItems(trianglesList);
    }

    private void initTriangler() {
        triangler = new DDATriangler(
                canvas.getGraphicsContext2D().getPixelWriter(),
                new MonotoneTriangleColorer(HTMLColors.BLUE));
    }

    private void initAddBtn() {
        addBtn.setOnAction(e -> {
            addTriangle();
        });
    }

    private void initClearBtn() {
        clearBtn.setOnAction(e -> {
            vertices.clear();
            triangles.clear();
            trianglesList.clear();
            clearCanvas();
        });
    }

    private void initCanvas() {
        canvasPane.widthProperty()
                .addListener((ov, oldValue, newValue) -> {
                    canvas.setWidth(newValue.doubleValue());
                    draw();
                });
        canvasPane.heightProperty()
                .addListener((ov, oldValue, newValue) -> {
                    canvas.setHeight(newValue.doubleValue());
                    draw();
                });
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D()
                .clearRect(
                        0,
                        0,
                        canvas.getWidth(),
                        canvas.getHeight());
    }

    private Vector2D randomVector() {
        final float x = rnd.nextFloat((float) canvas.getWidth());
        final float y = rnd.nextFloat((float) canvas.getHeight());

        return new Vector(x, y);
    }

    private float randomAngle() {
        return rnd.nextFloat(3.14f * 2);
    }

    private MovingVector toMovingVector(final Vector2D v) {
        return new MovingVector(v, randomAngle());
    }

    private void addTriangle() {
        final Vector2D v1 = randomVector();
        final Vector2D v2 = randomVector();
        final Vector2D v3 = randomVector();

        final Triangle t = new DynamicTriangle(v1, v2, v3);

        vertices.add(toMovingVector(v1));
        vertices.add(toMovingVector(v2));
        vertices.add(toMovingVector(v3));

        final KeepedTriangle kt = new KeepedTriangle(t);

        triangles.add(kt);
        trianglesList.add(kt);
    }

    private void move(final float deltaTime) {
        for (final MovingVector v : vertices) {
            v.move(deltaTime);
        }
    }

    private void draw() {
        clearCanvas();

        for (final KeepedTriangle kt : triangles) {
            kt.keeper.time(() -> {
                triangler.draw(kt.t);
            });
        }

        trianglesListView.refresh();
    }

    public AnchorPane root() {
        return root;
    }
}
