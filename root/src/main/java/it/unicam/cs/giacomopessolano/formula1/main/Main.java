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

package it.unicam.cs.giacomopessolano.formula1.main;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import it.unicam.cs.giacomopessolano.formula1.game.*;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;

import java.io.IOException;

/**
 * Main class of the application.
 */
public class Main {

    public static void main(String[] args) {
        GameManager game;
        TurnManager turnManager;
        GameInitializer initializer;
        GridInitializerFromTxt gridInitializer;
        PlayerInitializerFromTxt playerInitializer;
        UserInterface ui = new UserInterfaceCLI();

        while (true) {
            try {
                String trackName = ui.chooseTrack("root/src/main/resources");
                gridInitializer = new ArrayGridInitializerFromTxt();
                playerInitializer = new PlayerFormula1InitializerFromTxt();
                initializer = new GameInitializerFromTxt(trackName, gridInitializer, playerInitializer);
                turnManager = new TurnManagerStandard();
                game = new GameManagerStandard(initializer, turnManager);

                game.startGame();
                ui.displayGrid(game);
                break;
            } catch (UnrecognizedFileException e) {
            ui.errorMessage("The track was not recognized; check if you put it in the right folder.");
            } catch (IncorrectConfigurationException e) {
            ui.errorMessage("The track was formatted incorrectly; check instructions");
            } catch (IOException e) {
            ui.errorMessage("Something went wrong, please try again.");
            }
        }

        gameLoop(game, ui);

        System.exit(0);

    }

    /**
     * The game's standard loop. Displays each turn the game's current state and waits for the user to unpause
     * the simulation. When the game is over, asks the user if they want to play again on the same settings.
     *
     * @param game Game settings.
     * @param ui User interface.
     */
    private static void gameLoop(GameManager game, UserInterface ui) {
        while (true) {
            try {
                ui.pause();
                ui.turnMessage(game);
                //nextTurn updates the value of currentPlayer, so it must be checked before the turn logic
                if (game.getCurrentPlayer().hasCrashed()) {
                    game.nextTurn();
                } else {
                    game.nextTurn();
                    ui.displayGrid(game);
                }

                if (!game.isGameRunning()) {
                    ui.gameOverMessage(game);
                    if (ui.wantToPlayAgainMessage()) {
                        game.startGame();
                        ui.displayGrid(game);
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                ui.errorMessage("Something unexpected went wrong; " + e.getMessage());

                System.exit(-1);
            }
        }
    }
}
