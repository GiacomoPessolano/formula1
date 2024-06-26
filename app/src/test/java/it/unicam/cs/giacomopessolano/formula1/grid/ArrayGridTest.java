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

import it.unicam.cs.giacomopessolano.formula1.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayGridTest {

    private ArrayGrid grid;

    @BeforeEach
    void setUp() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //for this class' tests we state of the cell is not important
                cells[i][j] = new Cell(CellState.OFFTRACK);
            }
        }
        grid = new ArrayGrid(cells);
    }

    @Test
    void testGetCell() {
        Position position = new Position(1, 1);
        Cell cell = grid.getCell(position);

        assertNotNull(cell);
    }

    @Test
    void testGetWidth() {
        int width = grid.getWidth();

        assertEquals(3, width);
    }

    @Test
    void testGetHeight() {
        int height = grid.getHeight();

        assertEquals(3, height);
    }

    @Test
    void testClone() {
        ArrayGrid clonedGrid = grid.clone();

        assertNotSame(grid, clonedGrid);

        assertEquals(grid.getWidth(), clonedGrid.getWidth());
        assertEquals(grid.getHeight(), clonedGrid.getHeight());

        for (int i = 0; i < clonedGrid.getWidth(); i++) {
            for (int j = 0; j < clonedGrid.getHeight(); j++) {
                assertNotSame(grid.getGrid()[j][i], clonedGrid.getGrid()[j][i]);
                assertEquals(grid.getGrid()[j][i], clonedGrid.getGrid()[j][i]);
            }
        }

    }

}
