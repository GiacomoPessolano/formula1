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
        try {
            UserInterfaceCLI ui = new UserInterfaceCLI();
            String file = "C:\\Users\\Utente\\JavaProjects\\formula1\\root\\src\\main\\resources\\donut.txt";

            GameManager game = getGameManagerStandard(file);

            game.startGame();

            ValidatorStandard validator = new ValidatorStandard(game.getGrid(), game.getPlayerPositions());
            if (!validator.performAllChecks()) throw new IncorrectConfigurationException(
                    "Configuration failed validation.");

            ui.displayGrid(game);
            while (game.isGameRunning()) {
                Thread.sleep(5000);
                ui.turnMessage(game);
                Thread.sleep(1000);
                game.nextTurn();
                ui.displayGrid(game);
                if (!game.isGameRunning()) {
                    ui.gameOverMessage(game);
                    if (ui.wantToPlayAgainMessage()) {
                        game.startGame();
                    }
                }
            }
        } catch (IncorrectConfigurationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (UnrecognizedFileException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(3);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(5);
        }

        System.exit(0);
    }

    /**
     * Returns a GameManagerStandard from a .txt file.
     *
     * @param file Name of the file.
     * @return Game manager with TurnManagerStandard and GameInitializerFromTxt classes.
     * @throws IOException If there are errors while initializing the file.
     */
    private static GameManager getGameManagerStandard(String file) throws IOException {
        GameInitializerFromTxt initializer = new GameInitializerFromTxt(file, new ArrayGridInitializerFromTxt(),
                new PlayerFormula1InitializerFromTxt());
        TurnManager turnManager = new TurnManagerStandard();

        return new GameManagerStandard(initializer, turnManager);
    }
}
