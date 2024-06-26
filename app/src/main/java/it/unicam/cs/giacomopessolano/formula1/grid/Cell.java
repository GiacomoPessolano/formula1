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

package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.player.Player;

/**
 * Class representing a Cell of the playing Grid. Each cell has a state and can optionally contain a player.
 */
public class Cell implements Cloneable {

    /**
     * The state of the cell.
     */
    private final CellState state;

    /**
     * The player occupying the cell (null if empty).
     */
    private Player player;

    /**
     * Constructs a Cell with the specified initial state.
     *
     * @param state The initial state of the cell.
     */
    public Cell(CellState state) {
        this.state = state;
    }

    /**
     * Returns the current state of the cell.
     *
     * @return The current state of the cell.
     */
    public CellState getState() {
        return state;
    }

    /**
     * Occupies the cell with the specified player.
     *
     * @param player The player to occupy the cell.
     */
    public void occupy(Player player) {
        this.player = player;
    }

    /**
     * Checks if the cell is occupied.
     *
     * @return True if the player is null, false otherwise.
     */
    public boolean isOccupied() {
        return player != null;
    }

    /**
     * Removes the player from the cell, making it empty.
     */
    public void flushPlayer() {
        this.player = null;
    }

    /**
     * Retrieves the player currently occupying the cell.
     *
     * @return The player occupying the cell, or null if the cell is empty.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Compares a cell to another object.
     *
     * @param o Object to compare the cell to.
     * @return True if the object has the same state and player, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell cell)) return false;

        if (!state.equals(cell.state)) return false;


        if (getPlayer() == null && cell.getPlayer() == null) {
            return true;
        } else if (getPlayer() == null || cell.getPlayer() == null) {
            // One player is null, the other is not
            return false;
        } else {
            return getPlayer().equals(cell.getPlayer());
        }
    }

    /**
     * Creates a deep copy of the cell, including its state and occupied player (if any).
     *
     * @return A cloned Cell object with the same state and player (if occupied).
     */
    @Override
    public Cell clone() {
        try {
            Cell cloned = (Cell) super.clone();

            if (this.player != null) {
                cloned.player = this.player.clone();
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
