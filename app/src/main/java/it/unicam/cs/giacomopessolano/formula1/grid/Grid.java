package it.unicam.cs.giacomopessolano.formula1.grid;

public interface Grid {

    int getWidth();

    int getHeight();

    Cell getCell(int x, int y);

}
