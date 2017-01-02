package br.com.javafx.tests.game.tictactoe.players;

import br.com.javafx.tests.game.tictactoe.Game;
import br.com.javafx.tests.game.tictactoe.State;

public class Player {

    private String name;
    private State playerState;

    public Player(String name) {
        this.name = name;
    }

    public void afterPlayer(Game game) {
    }

    public String getName() {
        return name;
    }

    public void setPlayerState(State playerState) {
        this.playerState = playerState;
    }

    public State getPlayerState() {
        return playerState;
    }
}
