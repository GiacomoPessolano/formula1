package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;


public class ValidatorStandard implements Validator {

    private final Grid grid;
    private final Map<Player, Position> startingPositions;
    private final int playerNumber;
    private final ArrayList<Cell> startCells;
    private final ArrayList<Cell> endCells;

    public ValidatorStandard(Grid grid, Map<Player, Position> startingPositions) {
        this.grid = grid;
        this.playerNumber = startingPositions.keySet().size();
        this.startingPositions = startingPositions;
        this.startCells = findTypeCells(CellState.START);
        this.endCells = findTypeCells(CellState.END);
    }

    @Override
    public boolean performAllChecks() {
        return isPlayerNumberValid() && isStartNumberCorrect() && isEndNumberCorrect()
                && arePlayerPositionsCorrect() && areStartingPositionsDifferent();
    }

    private boolean isPlayerNumberValid() {
        return playerNumber <= 4 && playerNumber > 0;
    }

    private boolean isStartNumberCorrect() {
        return !startCells.isEmpty() && playerNumber >= 1 && startCells.size() >= playerNumber;
    }

    private boolean isEndNumberCorrect() {
        return !endCells.isEmpty();
    }

    private boolean arePlayerPositionsCorrect() {
        for (Position position : startingPositions.values()) {
            if (!startCells.contains(grid.getCell(position))) {
                return false;
            }
        }
        return true;
    }

    private boolean areStartingPositionsDifferent() {
        HashSet<Position> positionSet = new HashSet<>(startingPositions.values());
        return positionSet.size() == startingPositions.size();
    }

    private ArrayList<Cell> findTypeCells(CellState type) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                Cell cell = grid.getCell(new Position(row, col));
                if (cell.getState() == type) {
                    cells.add(cell);
                }
            }
        }
        return cells;
    }
}
