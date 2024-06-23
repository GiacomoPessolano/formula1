package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public class MovementCalculatorStandard {

    public Position calculateNewPosition(Position currentPosition, Move lastMove, Direction choice) {
        Move move = lastMove.update(choice);
        return new Position(currentPosition.x() + move.x(), currentPosition.y() + move.y());
    }

    public Position getCenter(Position currentPosition, Move lastMove) {
        return new Position(currentPosition.x() + lastMove.x(), currentPosition.y() + lastMove.y());
    }
}
