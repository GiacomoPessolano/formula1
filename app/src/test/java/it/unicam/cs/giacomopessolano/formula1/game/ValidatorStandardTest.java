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

import static org.junit.jupiter.api.Assertions.*;

import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ValidatorStandardTest {

    private Grid grid;
    private Map<Player, Position> startingPositions;
    private Player player1;
    private Player player2;
    private Player player3;
    private int maxSize = 120;

    @BeforeEach
    void setUp() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellState.TRACK);
            }
        }
        cells[0][0] = new Cell(CellState.START);
        cells[0][1] = new Cell(CellState.START);
        cells[2][2] = new Cell(CellState.END);
        grid = new ArrayGrid(cells);

        startingPositions = new HashMap<>();
        Strategy strat = new StrategyDumb();
        player1 = new PlayerFormula1("Player1", strat, Direction.CENTER);
        player2 = new PlayerFormula1("Player2", strat, Direction.CENTER);
        player3 = new PlayerFormula1("Player3", strat, Direction.CENTER);
    }

    @Test
    void testPerformAllChecks_Valid() {
        startingPositions.put(player1, new Position(0, 0));
        startingPositions.put(player2, new Position(1, 0));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        assertTrue(validator.performAllChecks());
    }

    @Test
    void testPerformAllChecks_InvalidPlayerNumber() {
        startingPositions.put(player1, new Position(0, 0));
        startingPositions.put(player2, new Position(1, 0));
        startingPositions.put(player3, new Position(1, 1));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        //there are more players than START cells
        assertFalse(validator.performAllChecks());
    }

    @Test
    void testPerformAllChecks_NoEndCells() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(CellState.TRACK);
            }
        }
        cells[0][0] = new Cell(CellState.START);
        cells[0][1] = new Cell(CellState.START);
        grid = new ArrayGrid(cells);

        startingPositions.put(player1, new Position(0, 0));
        startingPositions.put(player2, new Position(1, 0));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        assertFalse(validator.performAllChecks());
    }

    @Test
    void testPerformAllChecks_InvalidPlayerPositions() {
        startingPositions.put(player1, new Position(1, 0));
        startingPositions.put(player2, new Position(0, 1));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        assertFalse(validator.performAllChecks());
    }

    @Test
    void testPerformAllChecks_DuplicateStartingPositions() {
        startingPositions.put(player1, new Position(0, 0));
        startingPositions.put(player2, new Position(0, 0));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        assertFalse(validator.performAllChecks());
    }

    @Test
    void testPerformAllChecks_MaxSize() {
        maxSize = 1;
        startingPositions.put(player1, new Position(0, 0));
        ValidatorStandard validator = new ValidatorStandard(grid, startingPositions, maxSize, maxSize);

        assertFalse(validator.performAllChecks());
    }
}
