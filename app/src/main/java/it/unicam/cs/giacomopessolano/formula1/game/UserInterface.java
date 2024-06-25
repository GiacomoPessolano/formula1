package it.unicam.cs.giacomopessolano.formula1.game;

import java.io.IOException;

public interface UserInterface {

    String chooseTrack(String directory) throws IOException;

    void displayGrid(GameManager manager);

    void turnMessage(GameManager manager);

    void gameOverMessage(GameManager manager);

    boolean wantToPlayAgainMessage();
}
