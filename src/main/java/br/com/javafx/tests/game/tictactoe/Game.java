package br.com.javafx.tests.game.tictactoe;

import br.com.javafx.tests.game.tictactoe.players.Player;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private boolean finished = false;
    private Player player1;
    private Player player2;
    private Player currentPlayer = null;
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


    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player1.setPlayerState(State.X);
        this.player2 = player2;
        this.player2.setPlayerState(State.O);
        currentPlayer = player1;
    }

    public Node createGame() {
        Group root = new Group();

        GridPane gridPane = new GridPane();
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int column = i % 3;
            int size = 80;

            Sprite sprite = new Sprite(this, i, row, column, size);
            sprites.put(i, sprite);
            sprite.render(gridPane);
        }
        root.getChildren().add(gridPane);
        return root;
    }

    public boolean checkEndGame(Sprite sprite) {
        Sprite currentSprite = sprites.get(sprite.getId());
        for (int i = 0; i < winLines[sprite.getId()].length; i++) {
            int[] line = winLines[sprite.getId()][i];
            if(currentSprite.getState().equals(sprites.get(line[0]).getState()) && currentSprite.getState().equals(sprites.get(line[1]).getState())) {
                finishGame(String.format("\"%s\" WON!!", sprite.getOwner().getName()));
                return true;
            }
        }

        boolean allSpritesAreFilled = true;
        for (Sprite sprite1 : sprites.values()) {
            if (!sprite1.isFilled()) {
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
        for (Sprite sprite : sprites.values()) {
            sprite.finish();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Endgame!!");
        alert.setHeaderText("Endgame!!");
        alert.setContentText(text);

        alert.showAndWait();

        finished = true;
    }

    public void spriteSelected(Sprite sprite) {
        sprite.setState(currentPlayer.getPlayerState());
        sprite.setOwner(currentPlayer);
        if (!checkEndGame(sprite)) {
            if (player1.equals(currentPlayer)) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
            // Enable any bots to do his play
            currentPlayer.afterPlayer(this);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public Sprite getSprite(int move) {
        return sprites.get(move);
    }
}
