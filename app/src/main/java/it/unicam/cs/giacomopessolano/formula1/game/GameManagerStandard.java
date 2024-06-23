package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//todo default access for setters and constructor?

public class GameManagerStandard implements GameManager {

    private final GameInitializer initializer;
    private Grid grid;
    private Map<Player, Position> positionMap;
    private List<Player> players;
    private int turn;
    private boolean gameOver = false;
    private Player winner = null;

    public GameManagerStandard(GameInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public void start() throws IOException {
        this.grid = initializer.parseGrid();
        this.positionMap = initializer.parsePlayers();
        this.players = initializer.parseTurns();
        this.turn = 0;
    }

    @Override
    public void reset() throws IOException {
        gameOver = false;
        winner = null;
        start();
    }

    @Override
    public void nextTurn() {
        turn = (turn + 1) % players.size();
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Map<Player, Position> getPlayerPositions() {
        return positionMap;
    }

    @Override
    public Position getPlayerPosition(Player p) {
        return positionMap.get(p);
    }

    @Override
    public Player getCurrentPlayer(int turn) {
        return players.get(turn);
    }

    @Override
    public void gameOver(Player player) {
        gameOver = true;
        winner = player;
    }
}
