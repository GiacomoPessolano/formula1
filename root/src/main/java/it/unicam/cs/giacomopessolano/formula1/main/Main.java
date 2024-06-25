package it.unicam.cs.giacomopessolano.formula1.main;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import it.unicam.cs.giacomopessolano.formula1.game.*;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            UserInterfaceCLI ui = new UserInterfaceCLI();
            String file = "C:\\Users\\Utente\\JavaProjects\\formula1\\root\\src\\main\\resources\\donut.txt";

            GameManagerStandard game = getGameManagerStandard(file);

            game.startGame();
            while (game.isGameRunning()) {
                ui.displayGrid(game);
                Thread.sleep(5000);
                ui.turnMessage(game);
                Thread.sleep(1000);
                game.nextTurn();

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
        }

        System.exit(0);
    }

    private static GameManagerStandard getGameManagerStandard(String file) throws IOException {
        GameInitializerFromTxt initializer = new GameInitializerFromTxt(file, new ArrayGridInitializerFromTxt(),
                new PlayerBotInitializerFromTxt());
        TurnManagerStandard turnManager = new TurnManagerStandard();
        GameManagerStandard game = new GameManagerStandard(initializer, turnManager);
        ValidatorStandard validator = new ValidatorStandard(game.getGrid(), game.getPlayerPositions());
        if (!validator.performAllChecks()) throw new IncorrectConfigurationException(
                "Configuration failed validation.");

        return game;
    }
}
