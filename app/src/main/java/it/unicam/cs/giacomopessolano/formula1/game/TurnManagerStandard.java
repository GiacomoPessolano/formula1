package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

public class TurnManagerStandard implements TurnManager {
    @Override
    public CellState executeMove(Grid grid, Player player, Map<Player, Position> positions) {
        Direction choice = player.getStrategy().
                makeChoice(grid, player.getLastMove(), positions.get(player));

        player.setLastMove(choice);
        Move newMove = player.getLastMove();
        Position newPosition = calculateNewPosition(positions.get(player), newMove);

        updateGrid(grid, player, positions.get(player), newPosition);
        updatePlayerPosition(player, positions, newPosition);

        return moveResult(grid, positions.get(player), newPosition);
    }

    private void updateGrid(Grid grid, Player player, Position oldPosition, Position newPosition) {
        grid.getCell(oldPosition).flushPlayer();
        grid.getCell(newPosition).occupy(player);
    }

    private void updatePlayerPosition(Player player,
                                      Map<Player, Position> positions, Position newPosition) {
        positions.put(player, newPosition);
    }

    private Position calculateNewPosition(Position position, Move move) {
        return new Position(position.x() + move.x(), position.y() + move.y());
    }

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
