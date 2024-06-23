package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface MovementCalculator {

    Position calculateNewPosition(Position currentPosition, Move lastMove, Direction choice);

    Position getCenter(Position currentPosition, Move lastMove);

}
