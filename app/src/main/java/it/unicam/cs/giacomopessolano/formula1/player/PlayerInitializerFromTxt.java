package it.unicam.cs.giacomopessolano.formula1.player;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PlayerInitializerFromTxt {

    Map<Player, Position> parsePlayers(String filename) throws IOException;

    List<Player> parseTurns(String filename) throws IOException;

    Map<Player, Move> parseFirstMoves(String filename) throws IOException;

    default void validateFileExtension(String filename) throws IOException {
        if (!filename.endsWith(".txt")) {
            throw new IOException("File must have a .txt extension.");
        }
    }

}
