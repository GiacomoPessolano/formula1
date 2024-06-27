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

import it.unicam.cs.giacomopessolano.formula1.exceptions.GameOverException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import it.unicam.cs.giacomopessolano.formula1.game.*;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;
import it.unicam.cs.giacomopessolano.formula1.ui.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the application.
 */
public class Main extends Application {

    private GameManager game;
    private UserInterface ui;

    @Override
    public void start(Stage stage) {
        ui = new UserInterfaceJavaFX(stage);

        while (true) {
            try {
                String trackName = ui.chooseTrack("root/src/main/resources");
                GridInitializerFromTxt gridInitializer = new ArrayGridInitializerFromTxt();
                PlayerInitializerFromTxt playerInitializer = new PlayerFormula1InitializerFromTxt();
                GameInitializer initializer = new GameInitializerFromTxt(trackName, gridInitializer, playerInitializer);
                TurnManager turnManager = new TurnManagerStandard();
                game = new GameManagerStandard(initializer, turnManager);

                game.startGame();
                Validator validator = new ValidatorStandard(game.getGrid(), game.getPlayerPositions());
                if (validator.performAllChecks()) {
                    ui.displayGrid(game);
                    break;
                } else {
                    throw new IncorrectConfigurationException("");
                }
            } catch (UnrecognizedFileException e) {
                ui.errorMessage("The track was not recognized; check if you put it in the right folder.");
            } catch (IncorrectConfigurationException e) {
                ui.errorMessage("The track was formatted incorrectly; check instructions");
            } catch (IOException e) {
                ui.errorMessage("Something went wrong, please try again.");
            }
        }

        gameLoop();
    }

    /**
     * The game's standard loop. Displays each turn the game's current state and waits for the user to unpause
     * the simulation. When the game is over, asks the user if they want to play again on the same settings.
     */
    private void gameLoop() {
        while (true) {
            try {
                ui.pause();
                ui.turnMessage(game);
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
            } catch (GameOverException e) {
                System.exit(0);
            } catch (Exception e) {
                ui.errorMessage("Something unexpected went wrong; " + e.getMessage());
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}