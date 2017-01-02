package br.com.javafx.tests.game.tictactoe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Tic Tac Toe");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        new Game(root).createGame();

        theStage.show();
    }

}
