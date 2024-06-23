package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface MovementResult {

    void checkIfWithinGrid(Grid grid, Position position);

    void checkIfOccupied(Grid grid, Position position);

    //returns cell where traversal ends (not necessarily the new position's cell)
    CellState traverse(Grid grid, Position startingPosition, Position newPosition);

}
