package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.CellState;
import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface GameLogic {

    void movePlayer(Player p, Direction choice);

    void updateState(Player p, CellState traversedCell, Position currentPosition,
                     Position newPosition, Direction choice);
}
