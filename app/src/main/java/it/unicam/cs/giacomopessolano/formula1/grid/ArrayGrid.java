package it.unicam.cs.giacomopessolano.formula1.grid;

public class ArrayGrid implements Grid{

    private final Cell[][] grid;
    private final int width;
    private final int height;

    ArrayGrid(Cell[][] grid) {
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
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public Cell[][] getGrid(){
        return grid;
    }
}
