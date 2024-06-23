package it.unicam.cs.giacomopessolano.formula1.player;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PlayerInitializerFromTxt {

    void initialize(String filename) throws IOException;

    List<Player> getPlayers();

    Map<Player, Position> getPositions();

    default void validateFileExtension(String filename) throws IOException {
        if (!filename.endsWith(".txt")) {
            throw new IOException("File must have a .txt extension.");
        }
    }

}
