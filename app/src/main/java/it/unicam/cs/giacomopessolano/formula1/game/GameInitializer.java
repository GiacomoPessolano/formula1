package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Move;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GameInitializer {

    Map<Player, Position> parsePlayers() throws IOException;

    List<Player> parseTurns() throws IOException;

    Map<Player, Move> parseFirstMoves() throws IOException;

    Grid parseGrid() throws IOException;
}
