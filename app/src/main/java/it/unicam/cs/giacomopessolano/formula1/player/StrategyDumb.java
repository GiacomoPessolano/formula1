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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of Strategy that uses random chance to make its choice.
 */
public class StrategyDumb implements Strategy {

    Random rand = new Random();
    public static final String NAME = "DUMB";
    public static final String DESCRIPTION = "Player moves randomly.";

    /**
     * Returns a random movement choice as a Direction. If no move is available, it returns null.
     *
     * @param grid Grid where the movement takes place.
     * @param lastMove The last move executed by a player.
     * @param position The current position of a player on the grid.
     * @return The choice of Direction made.
     */
    @Override
    public Direction makeChoice(Grid grid, Move lastMove, Position position) throws NoPossibleMoveException {
        List<Direction> choices = possibleMoves(grid, lastMove, position);
        if (choices.isEmpty()) {
            throw new NoPossibleMoveException("There are no more possible moves.");
        }

        int index = rand.nextInt(choices.size());
        return choices.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Gives a list of all available moves. A move is unavailable if the cell is occupied by another player
     * and the resulting new position is within the grid.
     *
     * @param grid Grid where the movement takes place.
     * @param lastMove The last move executed by a player.
     * @param position The current position of a player on the grid.
     * @return List of available moves.
     */
    private List<Direction> possibleMoves(Grid grid, Move lastMove, Position position) {
        List<Direction> choices = new ArrayList<>();
        Position center = getCenter(lastMove, position);

        for (Direction direction : Direction.values()) {
            Position newPosition = center.neighbour(direction);
            if (newPosition.isValid(grid) &&
                    grid.getCell(newPosition).getPlayer() == null) {
                choices.add(direction);
            }
        }

        return choices;
    }

    /**
     * Gets the center of the eight possible Positions. It is determined by adding the player's last
     * move's x and y values to the player's current coordinates.
     *
     * @param lastMove The last move executed by a player.
     * @param position The current position of a player on the grid.
     * @return Center of the possible moves.
     */
    private Position getCenter(Move lastMove, Position position) {
        return new Position(position.x() + lastMove.x(),
                position.y() + lastMove.y());
    }
}
