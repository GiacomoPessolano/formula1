package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

public class StrategyDumb implements Strategy {
    @Override
    public Direction move(Move lastMove, Grid grid, Position position) {
        //todo
        return null;
    }

    public String getName() {
        return "DUMB";
    }

    public String getDescription() {
        return "Player that moves randomly.";
    }
}
