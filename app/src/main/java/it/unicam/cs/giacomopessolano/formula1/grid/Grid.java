package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface Grid extends Cloneable {

    Cell getCell(Position position);

    int getWidth();

    int getHeight();

    Grid clone();
}
