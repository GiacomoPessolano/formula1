package it.unicam.cs.giacomopessolano.formula1.grid;

//TODO javadoc

public interface Grid {

    int getWidth();

    int getHeight();

    Cell getCell(Position position);

}
