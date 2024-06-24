package it.unicam.cs.giacomopessolano.formula1.game;

import java.io.FileNotFoundException;

public interface UserInterface {

    String chooseTrack(String directory) throws FileNotFoundException;

    void displayGrid();

    void gameOverMessage();

    boolean wantToPlayAgain();
}
