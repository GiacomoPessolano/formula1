package it.unicam.cs.giacomopessolano.formula1.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerBotInitializerFromTxt implements PlayerInitializerFromTxt {

    @Override
    public Map<Player, Position> parsePlayers(String filename) throws IOException {
        validateFileExtension(filename);

        return findPlayerPositions(readPlayerData(filename));
    }

    private List<String[]> readPlayerData(String filename) throws IOException {
        List<String[]> playerData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> currentPlayerData = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!currentPlayerData.isEmpty()) {
                        playerData.add(currentPlayerData.toArray(new String[0]));
                        currentPlayerData.clear();
                    }
                } else {
                    currentPlayerData.add(line.trim());
                }
            }
            if (!currentPlayerData.isEmpty()) {
                playerData.add(currentPlayerData.toArray(new String[0]));
            }
        }

        return playerData;
    }

    private Map<Player, Position> findPlayerPositions(List<String[]> playerData) throws IOException {
        Map<Player, Position> players = new HashMap<>();

        for (String[] playerInfo : playerData) {
            for (String player : playerInfo) {
                String[] playerAttributes = player.split(" ");
                String name = playerAttributes[0];
                int x = Integer.parseInt(playerAttributes[1]);
                int y = Integer.parseInt(playerAttributes[2]);
                Strategy strategy = parseStrategy(playerAttributes[3]);
                Direction startingMove = parseDirection(playerAttributes[4]);
                players.put(new PlayerBot(name, strategy, startingMove), new Position(x, y));
            }
        }

        return players;
    }

    private Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            case "STANDARD" -> new StrategyStandard();
            case "CHEATER" -> new StrategyCheater();
            default -> throw new IOException("The strategy " + s + " is not valid.");
        };
    }

    private Direction parseDirection(String s) throws IOException {
        return switch (s) {
            case "UP" -> Direction.UP;
            case "DOWN" -> Direction.DOWN;
            case "LEFT" -> Direction.LEFT;
            case "RIGHT" -> Direction.RIGHT;
            default -> throw new IOException("The starting direction " + s + " is not valid.");
        };
    }

}