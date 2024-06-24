package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

public record Position(int x, int y) implements Cloneable {

    public Position neighbour(Direction direction) {
        return new Position(x + direction.x(), y + direction.y());
    }

    public boolean isValid(Grid grid) {
        int width = grid.getWidth();
        int height = grid.getHeight();


        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
