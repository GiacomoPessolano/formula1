package it.unicam.cs.giacomopessolano.formula1.main;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        return;
        /*
        String file = "example.txt";

        GameInitializerFromTxt initializer = new GameInitializerFromTxt(file, new ArrayGridInitializerFromTxt(),
                new PlayerBotInitializerFromTxt());
        Grid grid = initializer.parseGrid();
        Map<Player, Position> playerPositions = initializer.parsePlayers();
        List<Player> turns = initializer.parseTurns();

        ValidatorStandard validator = new ValidatorStandard(grid, playerPositions);
        if (!validator.performAllChecks()) throw new IOException();
        */
    }
}
