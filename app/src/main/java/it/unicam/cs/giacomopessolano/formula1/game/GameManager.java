package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

public interface GameManager {

    Grid getGrid();

    Map<Player, Position> getPlayerPositions();

    Position getPlayerPosition(Player p);

    Map<Player, Move> getLastMoves();

    Move getLastMove(Player p);

    void move(Player p, Move m);

}
