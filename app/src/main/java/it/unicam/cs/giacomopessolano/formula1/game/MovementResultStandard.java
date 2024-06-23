package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public class MovementResultStandard implements MovementResult {

    @Override
    public void checkIfWithinGrid(Grid grid, Position position) {
        int width = grid.getWidth();
        int height = grid.getHeight();

        if (position.x() < 0 || position.y() < 0 || position.x() >= width || position.y() >= height) {
            throw new IllegalMoveException("Cell is out of bounds.");
        }
    }

    @Override
    public void checkIfOccupied(Grid grid, Position position) {
        if (grid.getCell(position).getPlayer() != null) {
            throw new IllegalMoveException("Cell is occupied.");
        }
    }

    @Override
    public CellState traverse(Grid grid, Position startingPosition, Position newPosition) {
        int diffX = newPosition.x() - startingPosition.x();
        int diffY = newPosition.y() - startingPosition.y();

        //returns 1 or -1, which indicates the direction we move X/Y to reach the new X/Y coordinates
        int stepX = Integer.compare(diffX, 0);
        int stepY = Integer.compare(diffY, 0);

        int x = startingPosition.x();
        int y = startingPosition.y();
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
