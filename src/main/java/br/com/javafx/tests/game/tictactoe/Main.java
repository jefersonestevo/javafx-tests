package br.com.javafx.tests.game.tictactoe;

import br.com.javafx.tests.game.tictactoe.players.DumbPlayer;
import br.com.javafx.tests.game.tictactoe.players.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    private Pane pane;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("Tic Tac Toe");

        MenuBar mbar = generateMenuBar(stage);
        pane = new Pane();
        pane.getChildren().add(new Label("Tic Tac Toe"));

        VBox root = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().add(mbar);

        root.getChildren().add(hBox);
        root.getChildren().add(pane);
        Scene scene = new Scene(root, 240, 270);

        stage.setScene(scene);
        stage.show();
    }

    private MenuBar generateMenuBar(Stage stage) {
        MenuBar mbar = new MenuBar();
        mbar.prefWidthProperty().bind(stage.widthProperty());

        Menu menu = new Menu("Game");
        mbar.getMenus().add(menu);

        MenuItem newGameMenu = new MenuItem("New Game");
        newGameMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Create New Game!!");
                String s = "Are you sure you want to create a new game?";
                alert.setContentText(s);

                Optional<ButtonType> result = alert.showAndWait();

                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    // TODO - Let the user select between human and bot player and the player names
                    Node game = new Game(new Player("User 01"), new DumbPlayer()).createGame();
                    pane.getChildren().clear();
                    pane.getChildren().add(game);
                }
            }
        });
        menu.getItems().add(newGameMenu);

        MenuItem exitMenu = new MenuItem("Exit");
        exitMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit!!");
                String s = "Are you sure you want to exit?";
                alert.setContentText(s);

                Optional<ButtonType> result = alert.showAndWait();

                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    Platform.exit();
                }
            }
        });
        menu.getItems().add(exitMenu);
        return mbar;
    }

}
