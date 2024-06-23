package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.Map;

public interface GameManager {

    void start() throws IOException;

    void reset() throws IOException;

    void nextTurn();

    Grid getGrid();

    Map<Player, Position> getPlayerPositions();

    Position getPlayerPosition(Player p);;

    Player getCurrentPlayer(int turn);

    void gameOver(Player player);

}
