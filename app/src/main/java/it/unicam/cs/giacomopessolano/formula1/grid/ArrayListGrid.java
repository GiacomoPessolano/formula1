package it.unicam.cs.giacomopessolano.formula1.grid;

import java.util.ArrayList;

public class ArrayListGrid implements Grid{

    final ArrayList<ArrayList<Cell>> grid;
    private final int width;
    private final int height;

    public ArrayListGrid(ArrayList<ArrayList<Cell>> grid) {
        //TODO javadoc, default access control
        this.grid = grid;
        this.width = grid.getFirst().size();
        this.height = grid.size();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Cell getCell(Position position) {
        return grid.get(position.y()).get(position.x());
    }

    public ArrayList<ArrayList<Cell>> getGrid() {
        return grid;
    }
}
