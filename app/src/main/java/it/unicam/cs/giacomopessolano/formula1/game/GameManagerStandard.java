package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

//todo default access for setters and constructor?

public class GameManagerStandard implements GameManager {

    private final Grid grid;
    private final Map<Player, Position> players;
    boolean gameOver = false;
    Player winner = null;

    public GameManagerStandard(Grid grid, Map<Player, Position> players) {
        this.grid = grid;
        this.players = players;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Map<Player, Position> getPlayerPositions() {
        return players;
    }

    @Override
    public Position getPlayerPosition(Player p) {
        return players.get(p);
    }

    @Override
    public Move getLastMove(Player player) {
        return player.getLastMove();
    }

    @Override
    public void gameOver(Player player) {
        gameOver = true;
        winner = player;
    }
}
