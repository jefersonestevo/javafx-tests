package br.com.javafx.tests;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EarthPlanetMainTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage theStage) {
        theStage.setTitle("Earth");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1024, 768);
        root.getChildren().add(canvas);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final Image earth = new Image("earth.png", 80, 80, true, true);
        final Image moon = new Image("moon.png", 40, 40, true, true);
        final Image sun = new Image("sun.png", 120, 120, true, true);
        final Image space = new Image("space.png", 1024, 768, false, true);

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 446 + 300 * Math.cos(t);
                double y = 346 + 300 * Math.sin(t);

                double xLua = (x + 20) + 65 * Math.cos(t * 10);
                double yLua = (y + 20) + 65 * Math.sin(t * 10);

                // background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);
                gc.drawImage(moon, xLua, yLua);
                gc.drawImage(sun, 446, 346);
            }
        }.start();

        theStage.show();
    }
}