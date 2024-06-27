/*
 * MIT License
 *
 * Copyright (c) 2024 Giacomo Pessolano
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.cs.giacomopessolano.formula1.ui;

import it.unicam.cs.giacomopessolano.formula1.game.GameManager;

import java.io.IOException;

/**
 * Interface to interact with the user and show the game's progress.
 */
public interface UserInterface {

    /**
     * Lets the user choose a track.
     *
     * @param dir The directory where tracks are located.
     * @return The chosen track.
     * @throws IOException If something goes wrong while choosing a track.
     */
    String chooseTrack(String dir) throws IOException;

    /**
     * Displays a game's grid.
     *
     * @param manager Game whose grid is displayed.
     */
    void displayGrid(GameManager manager);

    /**
     * Displays a message to indicate the next turn.
     *
     * @param manager Game whose turn is displayed.
     */
    void turnMessage(GameManager manager);

    /**
     * Pauses the game.
     */
    void pause();

    /**
     * Displays a message to indicate the end of the game.
     *
     * @param manager Game whose results are displayed.
     */
    void gameOverMessage(GameManager manager);

    /**
     * Interacts with the user to ask if they want to play again.
     *
     * @return True if the answer is positive, false otherwise.
     */
    boolean wantToPlayAgainMessage();

    /**
     * Generic error message.
     *
     * @param message Message displayed.
     */
    void errorMessage(String message);
}