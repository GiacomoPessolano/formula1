/*
 * MIT License
 *
 * Copyright (c) 2024 Giacomo Pessolano
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of GameManager that uses the grid and players of an initializer and stores their changes
 * throughout the game. The turn logic and updates to the data structures are handled by a TurnManager.
 */
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

    /**
     * Constructs a GameManagerStandard with its initializer and turn manager. The constructor creates
     * immutable copies of the grid and players but doesn't start the game on its own.
     *
     * @param initializer Initializer to retrieve the grid and the players.
     * @param turnManager TurnManager to handle the game's logic.
     */
    public GameManagerStandard(GameInitializer initializer, TurnManager turnManager) {
        this.turnManager = turnManager;
        this.originalGrid = initializer.parseGrid();
        this.originalPlayers = initializer.parseTurns();
        this.originalPositions = initializer.parsePlayers();
    }

    /**
     * Starts the game. Can be used to reset the game when it's over by cloning the original data.
     */
    @Override
    public void startGame() {
        if (isGameRunning) return;

        isGameRunning = true;
        winner = null;
        crashedPlayers = 0;
        turn = 0;
        cloneData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Grid getGrid() {
        return grid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Player, Position> getPlayerPositions() {
        return playerPositions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return players.get(turn);
    }

    /**
     * Returns the player's turn as his ID.
     *
     * @param player Player to identify.
     * @return ID of the specified player.
     */
    @Override
    public String getID(Player player) {
        return String.valueOf(players.indexOf(player) + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    /**
     * Executes a turn and checks for the win condition. The player's movement and the updates to
     * the data structures are the turn manager's responsibility.
     * The game ends when someone reaches the finish line or everyone has crashed.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameRunning() {
        return isGameRunning;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getWinner() {
        return winner;
    }

    /**
     * Instantiates the grid and player managing fields with clones of the original data structures.
     */
    private void cloneData() {
        this.grid = originalGrid.clone();

        players.clear();
        playerPositions.clear();
        for (Player player : originalPlayers) {
            Player clone = player.clone();
            players.add(clone);
            playerPositions.put(clone, originalPositions.get(player));
        }

    }
}
