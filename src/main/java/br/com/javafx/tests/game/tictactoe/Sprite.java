package br.com.javafx.tests.game.tictactoe;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Sprite {
    private Game game;
    private Button button;
    private int id;
    private int row;
    private int column;
    private State state = State.BLANK;

    public Sprite(Game game, int id, int row, int column, int size) {
        this.game = game;
        this.id = id;
        this.button = new Button("");
        this.button.setMinWidth(size);
        this.button.setMinHeight(size);
        this.row = row;
        this.column = column;

        this.button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (!Sprite.this.game.isFinished() && !Sprite.this.isFilled()) {
                    Sprite.this.setState(State.X);
                    Sprite.this.game.update(Sprite.this.id);
                }
            }
        });
    }

    public void render(GridPane gridPane) {
        gridPane.add(button, column, row);
    }

    public boolean isFilled() {
        return !State.BLANK.equals(Sprite.this.state);
    }

    public void setState(State state) {
        this.state = state;
        this.button.setText(State.BLANK.equals(state) ? "" : state.name());
    }

    public State getState() {
        return state;
    }

    public void finish() {
        this.button.setOpacity(0.5);
    }
}
