package br.com.javafx.tests.game.moneybag;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Collect the Money Bags!");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);

        final ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code))
                            input.add(code);
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        final Sprite briefcase = new Sprite();
        briefcase.setImage(new Image("moneybag/briefcase.png", 50, 50, true, true));
        briefcase.setPosition(200, 0);

        final ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();

        int i = 0;
        outerWhile:
        while (i < 15) {
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;

            for (Sprite sprite : moneybagList) {
                if (sprite.getBoundary().intersects(px, py, 30, 30)) {
                    continue outerWhile;
                }
            }

            Sprite moneybag = new Sprite();
            moneybag.setImage(new Image("moneybag/moneybag.png", 30, 30, true, true));
            moneybag.setPosition(px, py);
            moneybagList.add(moneybag);
            i++;
        }

        final LongValue lastNanoTime = new LongValue(System.nanoTime());

        final IntValue score = new IntValue(0);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                briefcase.setVelocity(0, 0);
                if (input.contains("LEFT"))
                    briefcase.addVelocity(-125, 0);
                if (input.contains("RIGHT"))
                    briefcase.addVelocity(125, 0);
                if (input.contains("UP"))
                    briefcase.addVelocity(0, -125);
                if (input.contains("DOWN"))
                    briefcase.addVelocity(0, 125);

                briefcase.update(elapsedTime);

                // collision detection

                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext()) {
                    Sprite moneybag = moneybagIter.next();
                    if (briefcase.intersects(moneybag)) {
                        moneybagIter.remove();
                        score.value++;
                    }
                }

                // render
                int userCash = 100 * score.value;
                gc.clearRect(0, 0, 512, 512);
                if (moneybagList.isEmpty()) {
                    Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
                    gc.setFont(theFont);
                    gc.fillText(String.format("You won! ($%d)", userCash), 90, 250);
                    gc.strokeText(String.format("You won! ($%d)", userCash), 90, 250);
                } else {
                    briefcase.render(gc);

                    for (Sprite moneybag : moneybagList) {
                        moneybag.render(gc);
                    }

                    String pointsText = "Cash: $" + userCash;
                    gc.fillText(pointsText, 300, 36);
                    gc.strokeText(pointsText, 300, 36);
                }
            }
        }.start();

        theStage.show();
    }

    private class IntValue {
        private int value;

        public IntValue(int value) {
            this.value = value;
        }
    }

    private class LongValue {
        private long value;

        public LongValue(long value) {
            this.value = value;
        }
    }
}
