package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

@FunctionalInterface
public interface Strategy {

    Choice move(Move lastMove, Grid grid, Position position);

}
