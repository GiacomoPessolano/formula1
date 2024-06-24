package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface GameManager {

    void startGame();

    Grid getGrid();

    Player getCurrentPlayer(int turn);

    int getId(Player player);

    Position getPlayerPosition(Player player);

    void nextTurn();

    boolean hasGameStarted();

    Player getWinner();

}
