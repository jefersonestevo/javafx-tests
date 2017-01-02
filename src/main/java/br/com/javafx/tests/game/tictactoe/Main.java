package br.com.javafx.tests.game.tictactoe;

import br.com.javafx.tests.game.tictactoe.players.DumbPlayer;
import br.com.javafx.tests.game.tictactoe.players.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Tic Tac Toe");

        Scene theScene = new Game(new Player("User 01"), new DumbPlayer()).createGame();
//        Scene theScene = new Game(new Player("User 01"), new Player("User 02")).createGame();
        theStage.setScene(theScene);

        theStage.show();
    }

}
