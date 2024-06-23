package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.player.Choice;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public class GameLogicStandard implements GameLogic {

    private final GameManager manager;

    public GameLogicStandard(GameManager manager) {
        this.manager = manager;
    }

    @Override
    public void movePlayer(Player player, Choice choice) {
        Move lastMove = manager.getLastMove(player);
        Position currentPosition = manager.getPlayerPosition(player);
        Position center = getCenter(currentPosition, lastMove);

        if (isOiled(center)) {
            updatePosition(player, center);
            return;
        }

        Move move = lastMove.update(choice);
        Position newPosition = new Position
                (currentPosition.x() + move.x(), currentPosition.y() + move.y());
        checkIfOccupied(newPosition);
        manager.getGrid().getCell(currentPosition).flushPlayer();

        updatePosition(player, newPosition);
        updateLastMove(player, move);
    }

    @Override
    public void updatePosition(Player player, Position position) {
        manager.setPlayerPosition(player, position);
        manager.getGrid().getCell(position).occupy(player);
    }

    @Override
    public void updateLastMove(Player player, Move move) {
        manager.setLastMove(player, move);
    }

    private Position getCenter(Position currentPosition, Move lastMove) {
        return new Position(currentPosition.x() + lastMove.x(), currentPosition.y() + lastMove.y());
    }

    private boolean isOiled(Position position) {
        return manager.getGrid().getCell(position).getState().equals(CellState.OILED);
    }

    private void checkIfOccupied(Position position) {
        if (manager.getGrid().getCell(position).getPlayer() != null)
            throw new IllegalMoveException("Cell is occupied");
    }
}
