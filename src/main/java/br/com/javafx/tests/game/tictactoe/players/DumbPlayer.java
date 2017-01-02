package br.com.javafx.tests.game.tictactoe.players;

import br.com.javafx.tests.game.tictactoe.Game;
import br.com.javafx.tests.game.tictactoe.Sprite;

public class DumbPlayer extends Player {

    public static int counter = 1;

    public DumbPlayer() {
        this("bot-" + counter++);
    }

    public DumbPlayer(String name) {
        super(name);
    }

    @Override
    public void afterPlayer(Game game) {
        while (!game.isFinished()) {
            int move = Double.valueOf(Math.random() * 9).intValue();
            Sprite sprite = game.getSprite(move);
            if (sprite.isFilled()) {
                continue;
            }
            game.spriteSelected(sprite);
            game.checkEndGame(sprite);
            break;
        }
    }
}
