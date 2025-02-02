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
 * Interface that describes a grid made of Cell objects where the game takes place.
 */
public interface Grid extends Cloneable {

    /**
     * Returns the Cell at a given position.
     *
     * @param position Position on the grid.
     * @return Cell on the given Position's coordinates.
     */
    Cell getCell(Position position);

    /**
     * Returns the width of the grid.
     *
     * @return Grid's width.
     */
    int getWidth();

    /**
     * Returns the height of the grid.
     *
     * @return Grid's height.
     */
    int getHeight();

    /**
     * {@inheritDoc}
     */
    Grid clone();
}
