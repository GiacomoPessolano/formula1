package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

public interface Strategy {

    Direction makeChoice(Grid grid, Move lastMove, Position position);

    String getName();

    String getDescription();

}


//todo: an interactive strategy that uses an input interface somehow
