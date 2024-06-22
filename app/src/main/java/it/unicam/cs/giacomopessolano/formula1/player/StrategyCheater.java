package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc, move

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.Position;

public class StrategyCheater implements Strategy {
    @Override
    public String getName() {
        return "Cheater";
    }

    @Override
    public String getDescription() {
        return "Standard player that will periodically oil the road nearby him.";
    }

    @Override
    public Move move(Move lastMove, Position currentPosition, Grid grid) {
        return null;
    }
}
