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

package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.exceptions.NoPossibleMoveException;
import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

/**
 * Implementation of TurnManager to handle the game's logic. Updates a grid and the player's position
 * on said grid according to the player's move (which is the responsibility of their Strategy interface).
 * The player's action yields a result based on the type of cells he traversed.
 */
public class TurnManagerStandard implements TurnManager {

    /**
     * Updates the provided data structures based on the player's movement. Using the player's last
     * move, 'inertia' is applied to the movement. If no move is possible, it's considered as if
     * the player traversed an OFFTRACK cell.
     *
     * @param grid Grid to update.
     * @param player Player that moves on the grid.
     * @param positions Map of players to their positions.
     * @return CellState indicating the player's traversed cells.
     */
    @Override
    public CellState executeMove(Grid grid, Player player, Map<Player, Position> positions) {
        Position oldPosition = positions.get(player);

        Direction choice;
        try {
            choice = player.getStrategy().
                    makeChoice(grid, player.getLastMove(), oldPosition);
        } catch (NoPossibleMoveException e) {
            return CellState.OFFTRACK;
        }

        player.updateLastMove(choice);
        Move newMove = player.getLastMove();
        Position newPosition = calculateNewPosition(positions.get(player), newMove);

        updateGrid(grid, player, oldPosition, newPosition);
        updatePlayerPosition(player, positions, newPosition);

        return moveResult(grid, oldPosition, newPosition);
    }

    /**
     * Updates the grid by placing a player on a cell and freeing the old one.
     *
     * @param grid Grid to update.
     * @param player Player that moved on the grid.
     * @param oldPosition Player's old position.
     * @param newPosition Player's new position.
     */
    private void updateGrid(Grid grid, Player player, Position oldPosition, Position newPosition) {
        grid.getCell(oldPosition).flushPlayer();
        grid.getCell(newPosition).occupy(player);
    }

    /**
     * Updates player position on the Map.
     *
     * @param player Player that moved on the grid.
     * @param positions Mapping of players to their positions.
     * @param newPosition Player's new position.
     */
    private void updatePlayerPosition(Player player,
                                      Map<Player, Position> positions, Position newPosition) {
        positions.put(player, newPosition);
    }

    /**
     * Calculates the player's new position after moving. It's calculated using the move's values added
     * to the Position's coordinates.
     *
     * @param position Player's current position.
     * @param move Player's movement.
     * @return The new Position.
     */
    private Position calculateNewPosition(Position position, Move move) {
        return new Position(position.x() + move.x(), position.y() + move.y());
    }

    /**
     * Returns a CellState based on the traversed cells. The method will check all the cells between
     * the old and new Positions. It will calculate the horizontal movement before the vertical one.
     * As soon as an END or OFFTRACK cell is passed it will be returned.
     * If none of the cells traversed are END or OFFTRACK, TRACK will be returned.
     *
     * @param grid Game's grid.
     * @param oldPosition Old position where the movement started.
     * @param newPosition New position where the movement ended.
     * @return END or OFFTRACK if any cell had their state, TRACK otherwise.
     */
    private CellState moveResult(Grid grid, Position oldPosition, Position newPosition) {
        int diffX = newPosition.x() - oldPosition.x();
        int diffY = newPosition.y() - oldPosition.y();

        //returns 1 or -1, which indicates the direction we move X/Y to reach the new X/Y coordinates
        int stepX = Integer.compare(diffX, 0);
        int stepY = Integer.compare(diffY, 0);

        int x = oldPosition.x();
        int y = oldPosition.y();
        while (x != newPosition.x() || y != newPosition.y()) {

            if (x == newPosition.x()) y += stepY;
            else x += stepX;

            CellState cell = grid.getCell(new Position(x, y)).getState();
            if (cell.equals(CellState.END) || cell.equals(CellState.OFFTRACK)) {
                return cell;
            }

        }

        return CellState.TRACK;
    }

}
