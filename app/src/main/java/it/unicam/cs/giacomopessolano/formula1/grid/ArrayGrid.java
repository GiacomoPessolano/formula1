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

/**
 * Implementation of the Grid interface using a 2D array of Cell objects.
 */
public class ArrayGrid implements Grid {

    private final Cell[][] grid;
    private final int width;
    private final int height;

    /**
     * Constructs an ArrayGrid with the given 2D array of Cell objects.
     *
     * @param grid The 2D array of Cell objects representing the grid.
     */
    public ArrayGrid(Cell[][] grid) {
        this.grid = grid;
        this.width = grid[0].length;
        this.height = grid.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(Position position) {
        return grid[position.y()][position.x()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Creates a deep copy (clone) of the ArrayGrid object, including its grid of cells.
     *
     * @return A cloned ArrayGrid object with an independent copy of the grid.
     */
    @Override
    public ArrayGrid clone() {
        Cell[][] newGrid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newGrid[i][j] = grid[i][j].clone();
            }
        }

        return new ArrayGrid(newGrid);
    }

    /**
     * Returns the 2D array of Cells that composes the grid.
     *
     * @return 2D array of Cells.
     */
    public Cell[][] getGrid(){
        return grid;
    }
}