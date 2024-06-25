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

/**
 * Enumeration representing directions on a grid.
 * Each direction has associated x and y coordinate changes.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UPLEFT(-1, -1),
    UPRIGHT(1, -1),
    DOWNLEFT(-1, 1),
    DOWNRIGHT(1, 1),
    CENTER(0, 0);

    private final int x;
    private final int y;

    /**
     * Constructs a Direction with the specified x and y coordinate changes.
     *
     * @param x Horizontal coordinate change.
     * @param y Vertical coordinate change.
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the horizontal coordinate change associated with this direction.
     *
     * @return Horizontal coordinate change.
     */
    public int x() {
        return x;
    }

    /**
     * Returns the vertical coordinate change associated with this direction.
     *
     * @return Vertical coordinate change.
     */
    public int y() {
        return y;
    }
}
