package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc, move

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.Position;

public class StrategyStandard implements Strategy {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public String getDescription() {
        return "Looks for a safe route to the destination.";
    }

    @Override
    public Move move(Move lastMove, Position currentPosition, Grid grid) {
        return null;
    }
}
