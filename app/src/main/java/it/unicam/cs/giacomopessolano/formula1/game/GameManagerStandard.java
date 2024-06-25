package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManagerStandard implements GameManager {

    private final Grid originalGrid;
    private final List<Player> originalPlayers;
    private final  Map<Player, Position> originalPositions;

    private Grid grid;
    //originals might have different implementations of List and Map; it's unimportant
    private final List<Player> players = new ArrayList<>();
    private final Map<Player, Position> playerPositions = new HashMap<>();
    private final TurnManager turnManager;
    private boolean isGameRunning;
    private int crashedPlayers;
    Player winner;
    private int turn;

    public GameManagerStandard(GameInitializer initializer, TurnManager turnManager) throws IOException {
        this.turnManager = turnManager;
        this.originalGrid = initializer.parseGrid();
        this.originalPlayers = initializer.parseTurns();
        this.originalPositions = initializer.parsePlayers();
    }

    @Override
    public void startGame() {
        if (isGameRunning) return;

        isGameRunning = true;
        winner = null;
        crashedPlayers = 0;
        turn = 0;
        cloneData();
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
    public Player getCurrentPlayer() {
        return players.get(turn);
    }

    @Override
    public int getId(Player player) {
        return players.indexOf(player) + 1;
    }

    @Override
    public Position getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    @Override
    public void nextTurn() {
        if (!isGameRunning) return;
        Player currentPlayer = getCurrentPlayer();

        if (!currentPlayer.hasCrashed()) {
            CellState result = turnManager.executeMove(grid, currentPlayer, playerPositions);

            if (result.equals(CellState.OFFTRACK)) {
                currentPlayer.crash();
                crashedPlayers++;
                if (crashedPlayers == players.size()) {
                    isGameRunning = false;
                }
            }

            if (result.equals(CellState.END)) {
                winner = currentPlayer;
                isGameRunning = false;
            }

        }

        turn = (turn + 1) % players.size();
    }

    @Override
    public boolean isGameRunning() {
        return isGameRunning;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    private void cloneData() {
        this.grid = originalGrid.clone();

        players.clear();
        playerPositions.clear();
        for (Player player : originalPlayers) {
            Player clone = player.clone();
            players.add(clone);
            playerPositions.put(clone, originalPositions.get(player).clone());
        }

    }
}
