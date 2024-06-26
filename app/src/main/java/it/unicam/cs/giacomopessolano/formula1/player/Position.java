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

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

import java.util.Objects;

/**
 * Record to stores x and y coordinates on a grid.
 *
 * @param x Horizontal coordinate.
 * @param y Vertical coordinate.
 */
public record Position(int x, int y) {

    /**
     * Finds the neighbouring Position in the specified Direction.
     *
     * @param direction Direction of the wanted neighbour.
     * @return Neighbouring Position.
     */
    public Position neighbour(Direction direction) {
        return new Position(x + direction.x(), y + direction.y());
    }

    /**
     * Checks if the Position is inside a grid's boundaries.
     *
     * @param grid Grid to check the Position on.
     * @return True if Position is within the grid's boundaries, false otherwise.
     */
    public boolean isValid(Grid grid) {
        int width = grid.getWidth();
        int height = grid.getHeight();


        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Compares a Position to another object.
     *
     * @param obj Object to compare the Position to.
     * @return True if the object has the same coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position p) {
            return x == p.x() && y == p.y();
        }
        return false;
    }

    /**
     * Returns hash value calculated on a Position's coordinates.
     *
     * @return A hash value for this Position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
