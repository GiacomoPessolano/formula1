package it.unicam.cs.giacomopessolano.formula1.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerBotInitializerFromTxt implements PlayerInitializerFromTxt {

    private List<String[]> playerData;

    @Override
    public Map<Player, Position> parsePlayers(String filename) throws IOException {
        validateFileExtension(filename);

        if (playerData == null) {
            playerData = readPlayerData(filename);
        }
        return findPlayerPositions(playerData);
    }

    @Override
    public List<Player> parseTurns(String filename) throws IOException {
        validateFileExtension(filename);

        if (playerData == null) {
            playerData = readPlayerData(filename);
        }
        return findPlayerTurns(playerData);
    }

    public Map<Player, Move> parseFirstMoves(String filename) throws IOException {
        validateFileExtension(filename);

        if (playerData == null) {
            playerData = readPlayerData(filename);
        }
        Map<Player, Move> moves = new HashMap<>();

        for (String[] playerDataLine : playerData) {
            moves.put(new PlayerBot(playerDataLine[0], parseStrategy(playerDataLine[3])),
                    parseDirection(playerDataLine[5]));
        }

        return moves;
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
                players.put(new PlayerBot(name, strategy), new Position(x, y));
            }
        }

        return players;
    }

    private List<Player> findPlayerTurns(List<String[]> playerData) throws IOException {
        List<Player> turns = new ArrayList<>();

        for (String[] playerInfo : playerData) {
            for (String player : playerInfo) {
                String[] playerAttributes = player.split(" ");
                String name = playerAttributes[0];
                Strategy strategy = parseStrategy(playerAttributes[3]);
                turns.add(new PlayerBot(name, strategy));
            }
        }

        return turns;
    }

    private Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            case "STANDARD" -> new StrategyStandard();
            case "CHEATER" -> new StrategyCheater();
            default -> throw new IOException("The strategy " + s + " is not valid.");
        };
    }

    private Move parseDirection(String s) throws IOException {
        return switch (s) {
            case "UP" -> new Move(0, -1);
            case "DOWN" -> new Move(0, 1);
            case "LEFT" -> new Move(-1, 0);
            case "RIGHT" -> new Move(1, 0);
            default -> throw new IOException("The starting direction " + s + " is not valid.");
        };
    }

}