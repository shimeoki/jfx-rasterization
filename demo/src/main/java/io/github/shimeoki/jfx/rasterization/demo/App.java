package io.github.shimeoki.jfx.rasterization.demo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public App() {
    }

    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("app.fxml"));

        final Scene scene = new Scene(loader.load());

        stage.setTitle("jfx-rasterization demo");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
