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
 * Interface that describes a generic player of the Formula 1 game.
 */
public interface Player extends Cloneable {

    /**
     * Returns the name of the player.
     *
     * @return Player's name.
     */
    String getName();

    /**
     * Returns the strategy used by the player to decide movements.
     *
     * @return Player's strategy.
     */
    Strategy getStrategy();

    /**
     * Returns the player's last movement during the game.
     *
     * @return Player's last movement.
     */
    Move getLastMove();

    /**
     * Changes the last movement of the player by updating it with a specified Direction.
     *
     * @param choice Direction to update the last movement.
     */
    void updateLastMove(Direction choice);

    /**
     * Changes to player's state to indicate he has crashed.
     */
    void crash();

    /**
     * Checks whether the player has crashed.
     *
     * @return True if the player crashed, false otherwise.
     */
    boolean hasCrashed();

    /**
     * {@inheritDoc}
     */
    Player clone();
}
