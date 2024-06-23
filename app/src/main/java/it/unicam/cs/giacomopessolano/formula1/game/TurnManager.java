package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.player.Player;

public interface TurnManager {

    boolean hasCrashed(GameManager game, Player player);

    boolean hasWon(GameManager game, Player player);

}
