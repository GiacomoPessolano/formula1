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

import it.unicam.cs.giacomopessolano.formula1.exceptions.NoPossibleMoveException;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerStandardTest {

    private TurnManagerStandard turnManager;
    private Grid grid;
    private Player player;
    private Map<Player, Position> positions;
    private Position startPosition;

    @BeforeEach
    public void setUp() {
        turnManager = new TurnManagerStandard();
        startPosition = new Position(0, 0);
        positions = new HashMap<>();

        Cell[][] cells = new Cell[][]{
                {new Cell(CellState.START), new Cell(CellState.TRACK), new Cell(CellState.END)},
                {new Cell(CellState.TRACK), new Cell(CellState.TRACK), new Cell(CellState.TRACK)},
                {new Cell(CellState.TRACK), new Cell(CellState.TRACK), new Cell(CellState.TRACK)}
        };

        grid = new ArrayGrid(cells);
    }

    @Test
    public void testExecuteMoveWithValidMove() {
        // Create a player with a strategy to always move through inertia
        player = new PlayerFormula1("", (grid1, move, pos) -> Direction.CENTER, Direction.RIGHT);
        positions.put(player, startPosition);
        grid.getCell(startPosition).occupy(player);

        CellState result = turnManager.executeMove(grid, player, positions);

        Position expectedNewPosition = new Position(1, 0);
        assertEquals(CellState.TRACK, result);
        assertEquals(expectedNewPosition, positions.get(player));
        assertTrue(grid.getCell(expectedNewPosition).isOccupied());
        assertFalse(grid.getCell(startPosition).isOccupied());
    }

    @Test
    public void testExecuteMoveNoPossibleMove() {
        //the player has a strategy to always throw NoPossibleMoveException
        player = new PlayerFormula1("", (grid1, move, pos) -> {
            throw new NoPossibleMoveException("No possible move."); }, Direction.CENTER);
        positions.put(player, startPosition);
        grid.getCell(startPosition).occupy(player);

        CellState result = turnManager.executeMove(grid, player, positions);

        assertEquals(CellState.OFFTRACK, result);
        assertEquals(startPosition, positions.get(player));
        assertTrue(grid.getCell(startPosition).isOccupied());
    }

    @Test
    public void testExecuteMoveReachesEndCell() {
        //the player has a strategy to always move through inertia to reach END cell
        player = new PlayerFormula1("", (grid1, move, pos) -> Direction.CENTER, Direction.RIGHT);
        positions.put(player, startPosition);
        grid.getCell(startPosition).occupy(player);

        turnManager.executeMove(grid, player, positions); //move to (1, 0)
        CellState result = turnManager.executeMove(grid, player, positions); //move to (2, 0), END cell

        Position expectedNewPosition = new Position(2, 0);
        assertEquals(CellState.END, result);
        assertEquals(expectedNewPosition, positions.get(player));
        assertTrue(grid.getCell(expectedNewPosition).isOccupied());
        assertFalse(grid.getCell(startPosition).isOccupied());
    }

    @Test
    public void testExecuteMoveReachesOffTrackCell() {
        Cell[][] cells = new Cell[][]{
                {new Cell(CellState.START), new Cell(CellState.TRACK), new Cell(CellState.OFFTRACK)},
                {new Cell(CellState.TRACK), new Cell(CellState.TRACK), new Cell(CellState.TRACK)},
                {new Cell(CellState.TRACK), new Cell(CellState.TRACK), new Cell(CellState.TRACK)}
        };

        grid = new ArrayGrid(cells);

        //the player has a strategy to always move through inertia to reach OFFTRACK cell
        player = new PlayerFormula1("", (grid1, move, pos) -> Direction.CENTER, Direction.RIGHT);
        positions.put(player, startPosition);
        grid.getCell(startPosition).occupy(player);

        turnManager.executeMove(grid, player, positions); //move to (1, 0)
        CellState result = turnManager.executeMove(grid, player, positions); //move to (2, 0), OFFTRACK cell

        Position expectedNewPosition = new Position(2, 0);
        assertEquals(CellState.OFFTRACK, result);
        assertEquals(expectedNewPosition, positions.get(player));
        assertTrue(grid.getCell(expectedNewPosition).isOccupied());
        assertFalse(grid.getCell(startPosition).isOccupied());
    }

    @Test
    void testTraverseCells() {
        Cell[][] cells = new Cell[3][1];
        cells[0][0] = new Cell(CellState.START);
        cells[1][0] = new Cell(CellState.END);
        cells[2][0] = new Cell(CellState.OFFTRACK);

        Grid grid = new ArrayGrid(cells);
        Map<Player, Position> positions = new HashMap<>();

        //the player has a strategy to always move DOWN and should reach OFFTRACK through inertia
        player = new PlayerFormula1("", (grid1, move, pos) -> Direction.DOWN, Direction.DOWN);
        positions.put(player, new Position(0, 0));
        grid.getCell(startPosition).occupy(player);

        //the player traversed first END and only then OFFTRACK, therefore result should be END
        //due to how TurnManagerStandard calculates the resulting cell.
        CellState result = turnManager.executeMove(grid, player, positions);

        assertEquals(CellState.END, result);
        assertEquals(new Position(0, 2), positions.get(player));
        assertFalse(grid.getCell(startPosition).isOccupied());
    }
}
