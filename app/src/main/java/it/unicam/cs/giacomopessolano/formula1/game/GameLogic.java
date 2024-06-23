package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.player.Choice;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

public interface GameLogic {

    void movePlayer(Player p, Choice choice);

    void updatePosition(Player player, Position position);

    void updateLastMove(Player player, Move move);
}
