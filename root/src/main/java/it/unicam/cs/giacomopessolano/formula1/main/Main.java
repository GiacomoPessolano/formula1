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
import it.unicam.cs.giacomopessolano.formula1.exceptions.ValidationFailedException;
import it.unicam.cs.giacomopessolano.formula1.game.*;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;
import it.unicam.cs.giacomopessolano.formula1.ui.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class of the application.
 */
public class Main extends Application {

    /**
     * Class that keeps the game's state.
     */
    private GameManager game;
    /**
     * Class that displays the game as it progresses.
     */
    private UserInterface ui;
    /**
     * Class that updates the game.
     */
    private TurnManager turnManager;
    /**
     * Class that parses the grid.
     */
    private GridInitializerFromTxt gridInitializer;
    /**
     * Class that parses player info.
     */
    private PlayerInitializerFromTxt playerInitializer;
    /**
     * Class that initializes a game's grid and players from a file.
     */
    private GameInitializer initializer;
    /**
     * Validator to perform various checks on the data extracted from the configuration file.
     */
    private Validator validator;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the game by assigning variables, initializing the game and then starting the game loop.
     * Using -Pcli as a parameter in gradle run you can play in the CLI version of the app.
     *
     * @param primaryStage App's graphical display. Unused in the CLI version of the game.
     */
    @Override
    public void start(Stage primaryStage) {
        String mode = System.getProperty("ui.mode", "javafx");
        //the handler will define how interactive users input their moves. It should coincide with the UI
        InteractionHandler interactionHandler;
        if (mode.equalsIgnoreCase("cli")) {
            ui = new UserInterfaceCLI();
            interactionHandler = new InteractionHandlerCLI();
        } else {
            ui = new UserInterfaceJavaFX(primaryStage);
            primaryStage.setOnCloseRequest(event -> stop());
            interactionHandler = new InteractionHandlerJavaFX();
        }

        turnManager = new TurnManagerStandard();
        playerInitializer = new PlayerBotInteractiveInitializerFromTxt(interactionHandler);
        gridInitializer = new ArrayGridInitializerFromTxt();
        initializeGame();
        //the two game loops do the same thing but the CLI UI is best suited for the sequential version
        if (mode.equalsIgnoreCase("cli")) {
            gameLoopSequential();
        } else {
            gameLoop();
        }
    }

    /**
     * Stops the game.
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Initializes the game from a .txt file. Performs checks with a Validator class.
     */
    private void initializeGame() {
        while (true) {
            try {
                String trackName = ui.chooseTrack("root/src/main/resources");
                if (trackName.isEmpty()) {
                    stop();
                    break;
                }
                initializer = new GameInitializerFromTxt(trackName, gridInitializer, playerInitializer);
                validator = new ValidatorStandard(initializer.parseGrid(), initializer.parsePlayers(),
                        120, 120);
                game = new GameManagerStandard(initializer, validator);

                game.startGame();
                ui.displayGrid(game);
                break;
            } catch (UnrecognizedFileException | ValidationFailedException | IncorrectConfigurationException e) {
                ui.errorMessage(e.getMessage());
            } catch (Exception e) {
                ui.errorMessage("Something went wrong: " + e.getMessage());
            }
        }
    }

    /**
     * The game's standard loop. Displays each turn the game's current state and waits for the user to unpause
     * the simulation until the game is over.
     */
    private void gameLoop() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean keepGoing = true;
            while (keepGoing) {
                //game won't continue until Next Turn button is pressed
                ui.pause();
                Platform.runLater(() -> {
                    ui.turnMessage(game);
                    game.nextTurn(turnManager);
                    ui.displayGrid(game);
                });

                if (!game.isGameRunning()) {
                    Platform.runLater(() -> ui.gameOverMessage(game));
                    ui.pause();
                    keepGoing = false;
                }
            }
            executor.shutdown();
            stop();
        });

    }

    /**
     * A sequential implementation of the game's loop. Though the CLI version can work with the executor,
     * it's best suited for this implementation. JavaFX, on the hand, requires the executor.
     */
    private void gameLoopSequential() {
        try {
            while (game.isGameRunning()) {
                ui.pause();
                ui.turnMessage(game);
                game.nextTurn(turnManager);
                ui.displayGrid(game);

                if (!game.isGameRunning()) {
                    ui.gameOverMessage(game);
                    stop();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}