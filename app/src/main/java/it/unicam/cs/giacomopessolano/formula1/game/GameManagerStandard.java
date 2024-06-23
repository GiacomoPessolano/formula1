package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

//todo default access for setters?

public class GameManagerStandard implements GameManager {

    private final Grid grid;
    private final Map<Player, Position> playerPositions;
    private final Map<Player, Move> lastMoves;

    public GameManagerStandard(Grid grid, Map<Player, Position> playerPositions, Map<Player, Move> firstMoves) {
        this.grid = grid;
        this.playerPositions = playerPositions;
        this.lastMoves = firstMoves;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Map<Player, Position> getPlayerPositions() {
        return playerPositions;
    }

    @Override
    public Position getPlayerPosition(Player p) {
        return playerPositions.get(p);
    }

    @Override
    public void setPlayerPosition(Player player, Position position) {
        playerPositions.put(player, position);
    }

    @Override
    public Map<Player, Move> getLastMoves() {
        return lastMoves;
    }

    @Override
    public Move getLastMove(Player p) {
        return lastMoves.get(p);
    }

    @Override
    public void setLastMove(Player p, Move move) {
        lastMoves.put(p, move);
    }
}
