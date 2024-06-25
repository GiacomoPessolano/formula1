package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Map;

public interface GameManager {

    void startGame();

    Grid getGrid();

    Map<Player, Position> getPlayerPositions();

    Player getCurrentPlayer();

    int getId(Player player);

    Position getPlayerPosition(Player player);

    void nextTurn();

    boolean isGameRunning();

    Player getWinner();

}
