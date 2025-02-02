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

import java.util.Objects;

/**
 * Represents a Formula 1 player with a name, strategy and last movement.
 */
public class PlayerFormula1 implements Player {

    /**
     * Player's name.
     */
    private final String name;
    /**
     * Player's strategy.
     */
    private final Strategy strategy;
    /**
     * Player's last move.
     */
    private Move lastMove;
    /**
     * States if the Player crashed.
     */
    private boolean hasCrashed;

    /**
     * Constructs a Formula 1 player with the given name, strategy, and initial movement direction.
     *
     * @param name     The name of the player.
     * @param strategy The strategy used by the player.
     * @param choice   The initial movement direction chosen.
     */
    public PlayerFormula1(String name, Strategy strategy, Direction choice) {
        assert name != null;
        assert strategy != null;
        assert choice != null;

        this.name = name;
        this.strategy = strategy;
        lastMove = firstMove(choice);
        hasCrashed = false;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * {@inheritDoc}
     */
    public void crash() {
        hasCrashed = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCrashed() {
        return hasCrashed;
    }

    /**
     * {@inheritDoc}
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * {@inheritDoc}
     */
    public void updateLastMove(Direction choice) {
        assert choice != null;
        this.lastMove = lastMove.update(choice);
    }

    /**
     * Generates the first movement based on the initial direction choice.
     *
     * @param choice The initial direction choice.
     * @return The initial movement based on the chosen direction.
     */
    private Move firstMove(Direction choice) {
        assert choice != null;
        return new Move(0, 0).update(choice);
    }

    /**
     * Creates a copy of this player object.
     *
     * @return A copy of this player.
     */
    @Override
    public PlayerFormula1 clone() {
        try {
            return (PlayerFormula1) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Compares a Player to another object.
     *
     * @param obj Object to compare the Player to.
     * @return True if the object is of the PlayerFormula1 class, and it has the same name, strategy
     * of the Player, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerFormula1 other) {
            return name.equals(other.name) && strategy.equals(other.strategy);
        }
        return false;
    }

    /**
     * Returns hash value calculated on the Player's name and strategy.
     *
     * @return A hash value for this Player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, strategy);
    }
}