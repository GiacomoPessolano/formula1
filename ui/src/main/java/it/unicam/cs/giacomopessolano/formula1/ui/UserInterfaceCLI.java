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
import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.Scanner;

/**
 * Implementation of a user interface from the command line.
 */
public class UserInterfaceCLI implements UserInterface {

    /**
     * Class used for taking user input.
     */
    Scanner scanner;

    /**
     * Constructs the user interface and opens its input channel.
     */
    public UserInterfaceCLI() {
        scanner = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String chooseTrack(String dir) {
        assert dir != null;

        System.out.println("Choose your track. Available tracks are in the " + dir + " folder.");
        System.out.println("To add a track, put it in said folder.");
        return scanner.nextLine();
    }

    /**
     * Displays a grid as a String of characters.
     *
     * @param manager Game whose grid is displayed.
     */
    @Override
    public void displayGrid(GameManager manager) {
        assert manager != null;
        Grid grid = manager.getGrid();
        int width = grid.getWidth();
        int height = grid.getHeight();
        StringBuilder gridToString = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridToString.append(parseCellState(manager, grid.getCell(new Position(x, y))));
            }
            gridToString.append("\n");
        }

        System.out.println(gridToString);

    }

    /**
     * Displays a message to indicate the next turn. The message changes if the player has crashed.
     *
     * @param manager Game whose turn is displayed.
     */
    @Override
    public void turnMessage(GameManager manager) {
        assert manager != null;
        if (!manager.isGameRunning()) return;
        Player player = manager.getCurrentPlayer();
        String name = player.getName();

        if (player.hasCrashed()) {
            System.out.println(name + " has crashed.");
        } else {
            System.out.println("It's " + name + ", " + manager.getID(player) + " , turn.");
        }
    }

    /**
     * Stops the game until the user inputs anything.
     */
    @Override
    public void pause() {
        System.out.println("Enter any key to continue...");
        scanner.nextLine();
    }

    /**
     * Displays a message to indicate the end of the game and its winner.
     *
     * @param manager Game whose results are displayed.
     */
    @Override
    public void gameOverMessage(GameManager manager) {
        assert manager != null;
        System.out.println("The game has ended.");
        Player winner = manager.getWinner();

        if (manager.getWinner() == null) {
            System.out.println("Nobody won the game.");
        } else {
            System.out.println("The winner is " + manager.getWinner().getName() + "!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorMessage(String message) {
        assert message != null;
        System.out.println(message);
    }

    /**
     * Translates a cell state to a character. If the cell is occupied, the character will be the first
     * character of the player's ID.
     *
     * @param manager Game being played.
     * @param cell    Cell to examine.
     * @return Character representing the cell or player on the cell.
     */
    private char parseCellState(GameManager manager, Cell cell) {
        if (cell.isOccupied()) return String.valueOf(manager.getID(cell.getPlayer())).charAt(0);

        switch (cell.getState()) {
            case END -> {
                return '@';
            }
            case OFFTRACK -> {
                return 'X';
            }
            default -> {
                return ' ';
            }
        }
    }
}

