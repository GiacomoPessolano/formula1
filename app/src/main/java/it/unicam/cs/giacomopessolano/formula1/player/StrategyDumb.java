package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyDumb implements Strategy {

    Random rand = new Random();
    public static final String NAME = "DUMB";
    public static final String DESCRIPTION = "Player moves randomly.";

    @Override
    public Direction move(Grid grid, Move lastMove, Position position) {
        List<Direction> choices = possibleMoves(grid, lastMove, position);
        if (choices.isEmpty()) {
            throw new IllegalArgumentException("No possible moves");
        }

        int index = rand.nextInt(choices.size());
        return choices.get(index);
    }

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    private List<Direction> possibleMoves(Grid grid, Move lastMove, Position position) {
        List<Direction> choices = new ArrayList<Direction>();
        Position center = getCenter(grid, lastMove, position);

        for (Direction direction : Direction.values()) {
            Position newPosition = center.neighbour(direction);
            if (newPosition.isValid(grid) &&
                    grid.getCell(newPosition).getPlayer() == null) {
                choices.add(direction);
            }
        }

        return choices;
    }

    private Position getCenter(Grid grid, Move lastMove, Position position) {
        return new Position(position.x() + lastMove.x(),
                position.y() + lastMove.y());
    }
}
