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
 * Record that represents a movement in a two-dimensional space, storing horizontal and vertical components.
 * The components can be negative to indicate movement in the opposite direction.
 *
 * @param x Horizontal movement component.
 * @param y Vertical movement component.
 */
public record Move(int x, int y) {

    /**
     * Updates the current movement by adding the given Direction's x and y components.
     *
     * @param choice Direction to update the movement.
     * @return Updated Move instance with the added direction components.
     */
    public Move update(Direction choice) {
        return new Move(x + choice.x(), y + choice.y());
    }

    /**
     * Compares Move to another object.
     *
     * @param o   the reference object with which to compare.
     * @return True if they have the same x and y values, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Move m) {
            return m.x() == x() && m.y() == y;
        }
        return false;
    }

}
