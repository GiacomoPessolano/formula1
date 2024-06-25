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

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

/**
 * Interface that stores the game's current state and turn progression.
 */
public interface GameManager {

    /**
     * Starts the game. Can be used to reset the game when it's over.
     */
    void startGame();

    /**
     * Returns the game's playing grid.
     *
     * @return Game's grid.
     */
    Grid getGrid();

    /**
     * Returns a mapping of players to their positions on the grid. Checks for the positions validity
     * are not done by default.
     *
     * @return Player's mapping on the grid.
     */
    Map<Player, Position> getPlayerPositions();

    /**
     * Returns the player that will play the next turn.
     *
     * @return Next turn's player.
     */
    Player getCurrentPlayer();

    /**
     * Returns an ID that unequivocally identifies a player.
     *
     * @param player Player to identify.
     * @return ID of the specified player.
     */
    String getID(Player player);

    /**
     * Returns the current position of a player.
     *
     * @param player Player to find.
     * @return The specified player's position.
     */
    Position getPlayerPosition(Player player);

    /**
     * Executes a turn and checks for the win condition. The player's movement and the updates to
     * the data structures are not the interface's responsibility.
     */
    void nextTurn();

    /**
     * Checks whether the game is running.
     *
     * @return True if the game has started, false otherwise and when it's over.
     */
    boolean isGameRunning();

    /**
     * Returns the game's winner.
     *
     * @return The game's winner, null if nobody won or the game is running.
     */
    Player getWinner();

}
