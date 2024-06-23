package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.player.Direction;
import it.unicam.cs.giacomopessolano.formula1.player.Player;

public interface GameLogic {

    void movePlayer(Player p, Direction choice);
}
