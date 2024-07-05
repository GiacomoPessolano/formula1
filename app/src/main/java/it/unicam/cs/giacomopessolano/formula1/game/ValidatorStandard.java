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

import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of Validator that performs checks on a grid and the players mapped to said grid.
 * Here's a rundown of all checks performed by the class:
 * - grid size must not exceed the values put in the constructor;
 * - number of players must be higher than 0 but less than the number of START cells;
 * - the number of END cells must not be zero;
 * - the mapping of starting positions must be reflected on the cells of the grid;
 * - two players cannot have the same starting position;
 */
public class ValidatorStandard implements Validator {

    /**
     * Grid to validate.
     */
    private final Grid grid;
    /**
     * Positions to validate.
     */
    private final Map<Player, Position> startingPositions;
    /**
     * Max grid width allowed.
     */
    private final int maxWidth;
    /**
     * Max grid height allowed.
     */
    private final int maxHeight;
    /**
     * Number of players.
     */
    private final int playerNumber;
    /**
     * Cells with CellState.START type.
     */
    private final ArrayList<Cell> startCells;
    /**
     * Cells with CellState.END type.
     */
    private final ArrayList<Cell> endCells;

    /**
     * Constructs a ValidatorStandard with the objects it's supposed to check.
     *
     * @param grid Grid to check.
     * @param startingPositions Player positions to check.
     * @param maxWidth The maximum width allowed. Must be higher than 0.
     * @param maxHeight The maximum height allowed. Must be higher than 0.
     */
    public ValidatorStandard(Grid grid, Map<Player, Position> startingPositions, int maxWidth, int maxHeight) {
        assert maxWidth > 0 && maxHeight > 0;
        assert grid != null;
        assert startingPositions != null;

        this.grid = grid;
        this.playerNumber = startingPositions.keySet().size();
        this.startingPositions = startingPositions;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.startCells = findTypeCells(CellState.START);
        this.endCells = findTypeCells(CellState.END);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performAllChecks() {
        return isGridSizeAcceptable(maxWidth, maxHeight) && isPlayerNumberValid() && isEndNumberCorrect()
                && arePlayerPositionsCorrect() && areStartingPositionsDifferent();
    }

    /**
     * Checks whether the player number is acceptable for the grid.
     *
     * @return True if players are less or equal than START cells and more than zero, false otherwise.
     */
    private boolean isPlayerNumberValid() {
        return playerNumber <= startCells.size() && playerNumber > 0;
    }

    /**
     * Checks whether the grid size is of an acceptable size.
     *
     * @param maxWidth Maximum allowed width.
     * @param maxHeight Maximum allowed height.
     * @return True if grid height and width are less than the given values;
     */
    private boolean isGridSizeAcceptable(int maxWidth, int maxHeight) {
        return grid.getHeight() <= maxHeight && grid.getWidth() <= maxWidth;
    }

    /**
     * Checks whether there are any END cells in the grid.
     *
     * @return True if there is at least one END cell, false otherwise.
     */
    private boolean isEndNumberCorrect() {
        return !endCells.isEmpty();
    }

    /**
     * Checks whether the player's starting position match with the grid.
     *
     * @return True if the all players start on a START cell, false otherwise.
     */
    private boolean arePlayerPositionsCorrect() {
        for (Position position : startingPositions.values()) {
            if (!startCells.contains(grid.getCell(position))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the players' starting positions are all different.
     *
     * @return True if players start at different locations, false otherwise.
     */
    private boolean areStartingPositionsDifferent() {
        //Map.values() returns all the different values associated to keys in the Map
        //If there are fewer values than keys, there are some repeated starting positions.
        HashSet<Position> positionSet = new HashSet<>(startingPositions.values());
        return positionSet.size() == startingPositions.size();
    }

    /**
     * Looks for all instances of a specific type of cell in the grid.
     *
     * @param type CellState value to look for.
     * @return ArrayList of all cells of the specified type.
     */
    private ArrayList<Cell> findTypeCells(CellState type) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Cell cell = grid.getCell(new Position(x, y));
                if (cell.getState() == type) {
                    cells.add(cell);
                }
            }
        }
        return cells;
    }

    /**
     * Compares a ValidatorStandard to another object.
     *
     * @param obj Object to compare the validator to.
     * @return True if the object validates the same data structures, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValidatorStandard other) {
            return grid.equals(other.grid) && startingPositions.equals(other.startingPositions);
        }
        return false;
    }

    /**
     * Returns hash value calculated on a ValidatorStandard data structures.
     *
     * @return A hash value for this validator.
     */
    @Override
    public int hashCode() {
        return Objects.hash(grid, startingPositions);
    }
}
