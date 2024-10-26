package com.github.shimeoki.jfx_rasterization.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RasterizationApp extends Application {

    public RasterizationApp() {
    }

    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(
                RasterizationApp.class
                        .getResource("main.fxml"));

        final Scene scene = new Scene(loader.load(), 800, 600);

        stage.setTitle("rasterization");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
