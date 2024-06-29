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
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

import java.util.List;
import java.util.Random;

/**
 * Implementation of Strategy that uses random chance to make its choice.
 */
public final class StrategyDumb implements Strategy {

    /**
     * Random number generator.
     */
    Random rand = new Random();
    /**
     * Name of the strategy.
     */
    public static final String NAME = "DUMB";
    /**
     * Description of the strategy.
     */
    public static final String DESCRIPTION = "Player moves randomly.";

    /**
     * Returns a random movement choice as a Direction.
     *
     * @param grid Grid where the movement takes place.
     * @param lastMove The last move executed by a player.
     * @param position The current position of a player on the grid.
     * @return The chosen Direction.
     * @throws NoPossibleMoveException If there is no possible move to perform.
     */
    @Override
    public Direction makeChoice(Grid grid, Move lastMove, Position position) throws NoPossibleMoveException {
        List<Direction> choices = Strategy.possibleMoves(grid, lastMove, position);
        if (choices.isEmpty()) {
            throw new NoPossibleMoveException("There are no more possible moves.");
        }

        int index = rand.nextInt(choices.size());
        return choices.get(index);
    }

}
