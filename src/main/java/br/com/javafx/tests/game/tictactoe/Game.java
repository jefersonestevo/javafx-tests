package br.com.javafx.tests.game.tictactoe;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private Group parent;
    private boolean finished = false;
    private Map<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();

    /*
     * Determines if the last move resulted in a win for either player
     * board: is an array representing the board
     * lastMove: is the boardIndex of the last (most recent) move
     *  these are the boardIndexes:
     *
     *   0 | 1 | 2
     *  ---+---+---
     *   3 | 4 | 5
     *  ---+---+---
     *   6 | 7 | 8
     *
     * returns true if there was a win
     *
     * source: http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
     */
    int[][][] winLines = new int[][][]{
            new int[][]{new int[]{1, 2}, new int[]{4, 8}, new int[]{3, 6}},
            new int[][]{new int[]{0, 2}, new int[]{4, 7}},
            new int[][]{new int[]{0, 1}, new int[]{4, 6}, new int[]{5, 8}},
            new int[][]{new int[]{4, 5}, new int[]{0, 6}},
            new int[][]{new int[]{3, 5}, new int[]{0, 8}, new int[]{2, 6}, new int[]{1, 7}},
            new int[][]{new int[]{3, 4}, new int[]{2, 8}},
            new int[][]{new int[]{7, 8}, new int[]{2, 4}, new int[]{0, 3}},
            new int[][]{new int[]{6, 8}, new int[]{1, 4}},
            new int[][]{new int[]{6, 7}, new int[]{0, 4}, new int[]{2, 5}}
        };


    public Game(Group parent) {
        this.parent = parent;
    }

    public void createGame() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int column = i % 3;
            int size = 80;

            Sprite sprite = new Sprite(this, i, row, column, size);
            sprites.put(i, sprite);
            sprite.render(gridPane);
        }
        this.parent.getChildren().add(gridPane);
    }

    public void update(int id) {
        if (!checkEndGame(id)) {
            while (true) {
                // TODO - Implement some intelligence. It's really dumb
                int move = Double.valueOf(Math.random() * 9).intValue();
                Sprite sprite = sprites.get(move);
                if (sprite.isFilled()) {
                    continue;
                }
                sprite.setState(State.O);
                checkEndGame(move);
                break;
            }
        }
    }

    public boolean checkEndGame(int lastMove) {
        Sprite sprite = sprites.get(lastMove);
        for (int i = 0; i < winLines[lastMove].length; i++) {
            int[] line = winLines[lastMove][i];
            if(sprite.getState().equals(sprites.get(line[0]).getState()) && sprite.getState().equals(sprites.get(line[1]).getState())) {
                finishGame(String.format("\"%s\" WON!!", sprite.getState().name()));
                return true;
            }
        }

        boolean allSpritesAreFilled = true;
        for (Sprite currentSprite : sprites.values()) {
            if (!currentSprite.isFilled()) {
                allSpritesAreFilled = false;
                break;
            }
        }

        if (allSpritesAreFilled) {
            finishGame("X/O");
            return true;
        }

        return false;
    }

    private void finishGame(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        label.setLayoutX(50);
        label.setLayoutY(105);

        this.parent.getChildren().add(label);
        for (Sprite sprite : sprites.values()) {
            sprite.finish();
        }
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }
}
