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

package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.NoPossibleMoveException;
import it.unicam.cs.giacomopessolano.formula1.grid.ArrayGrid;
import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    private Grid grid;
    private StrategyDumb strategy;

    @BeforeEach
    void setUp() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell(CellState.TRACK);
            }
        }
        grid = new ArrayGrid(cells);
        strategy = new StrategyDumb();
    }

    @Test
    void testMakeChoiceOneMoveAvailable() {
        Cell[][] cells = new Cell[2][1];
        cells[0][0] = new Cell(CellState.TRACK);
        cells[0][0].occupy(new PlayerFormula1("", strategy, Direction.CENTER));
        cells[1][0] = new Cell(CellState.TRACK);
        ArrayGrid grid = new ArrayGrid(cells);

        try {
            //the choice is random, so the loop should amplify the chances of finding bugs
            for (int i = 0; i < 30; i++) {
                Direction direction = strategy.makeChoice(grid, new Move(0, 0), new Position(0, 0));
                assertEquals(Direction.DOWN, direction);
            }
        } catch (NoPossibleMoveException e) {
            throw new AssertionError("Unexpected NoPossibleMoveException", e);
        }
    }

    @Test
    void testMakeChoiceNoMoves() {
        try {
            //grid should have no possible moves, so every cell gets occupied
            for (int i = 0; i < grid.getWidth(); i++) {
                for (int j = 0; j < grid.getHeight(); j++) {
                    grid.getCell(new Position(i, j)).occupy(new PlayerFormula1(
                            "", strategy, Direction.CENTER));
                }
            }

            Direction direction = strategy.makeChoice(grid, new Move(0, 0), new Position(0, 0));
            fail("Expected NoPossibleMoveException, but got direction: " + direction);
        } catch (NoPossibleMoveException e) {
            //expected exception, test passes
        }
    }
}

