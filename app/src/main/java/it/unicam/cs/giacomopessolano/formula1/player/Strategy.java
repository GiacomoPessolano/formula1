package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.Position;

public interface Strategy {

    String getName();

    String getDescription();

    Move move(Move lastMove, Position currentPosition, Grid grid);
}
