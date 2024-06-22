package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc, move

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.Position;

public class StrategyDumb implements Strategy{
    @Override
    public String getName() {
        return "Dumb";
    }

    @Override
    public String getDescription() {
        return "Moves randomly.";
    }

    @Override
    public Move move(Move lastMove, Position currentPosition, Grid grid) {
        return null;
    }
}
