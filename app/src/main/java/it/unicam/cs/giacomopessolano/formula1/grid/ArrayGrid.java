package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.player.Position;

public class ArrayGrid implements Grid{

    private final Cell[][] grid;
    private final int width;
    private final int height;

    ArrayGrid(Cell[][] grid) {
        //TODO javadoc, default access control
        this.grid = grid;
        this.width = grid[0].length;
        this.height = grid.length;
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
        return grid[position.y()][position.x()];
    }

    public Cell[][] getGrid(){
        return grid;
    }
}