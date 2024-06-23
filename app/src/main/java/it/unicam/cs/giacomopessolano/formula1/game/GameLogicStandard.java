package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public class GameLogicStandard implements GameLogic {

    private final GameManager manager;
    private final MovementCalculator moveCalculator;
    private final MovementResult movementResult;

    public GameLogicStandard(GameManager manager, MovementCalculator moveCalculator,
                             MovementResult movementResult) {
        this.manager = manager;
        this.moveCalculator = moveCalculator;
        this.movementResult = movementResult;
    }

    @Override
    public void movePlayer(Player player, Direction choice) {
        Move lastMove = player.getLastMove();
        Position currentPosition = manager.getPlayerPosition(player);
        Position center = moveCalculator.getCenter(currentPosition, lastMove);

        if (isOiled(center)) {
            updatePosition(player, center);
            return;
        }

        Position newPosition = moveCalculator.calculateNewPosition(currentPosition, lastMove, choice);
        movementResult.checkIfWithinGrid(manager.getGrid(), newPosition);
        movementResult.checkIfOccupied(manager.getGrid(), newPosition);

        CellState traversedCell = movementResult.traverse(manager.getGrid(), currentPosition, newPosition);
        updateState(player, traversedCell, currentPosition, newPosition, choice);
        manager.nextTurn();
    }

    private void updateState(Player player, CellState traversedCell, Position currentPosition,
                            Position newPosition, Direction choice) {
        manager.getGrid().getCell(currentPosition).flushPlayer();
        switch (traversedCell) {
            case END -> {
                manager.gameOver(player);
            }
            case OFFTRACK -> {
                player.crash();
            }
            default -> {
                updatePosition(player, newPosition);
                updateLastMove(player, choice);
            }
        }
    }

    private void updatePosition(Player player, Position position) {
        manager.getPlayerPositions().put(player, position);
        manager.getGrid().getCell(position).occupy(player);
    }

    private void updateLastMove(Player player, Direction choice) {
        player.setLastMove(choice);
    }

    private boolean isOiled(Position position) {
        return manager.getGrid().getCell(position).getState().equals(CellState.OILED);
    }
}
