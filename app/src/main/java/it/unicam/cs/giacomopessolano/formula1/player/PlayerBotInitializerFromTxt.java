package it.unicam.cs.giacomopessolano.formula1.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo supplier for Strategy and Direction, playerFactory?

public class PlayerBotInitializerFromTxt implements PlayerInitializerFromTxt {

    private Map<Player, Position> playerPositionMap;
    private List<Player> players;

    @Override
    public void initialize(String filename) throws IOException {
        validateFileExtension(filename);
        List<String[]> playerData = readPlayerData(filename);
        findPlayers(playerData);
    }


    @Override
    public Map<Player, Position> getPositions() {
        return playerPositionMap;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
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

    private void findPlayers(List<String[]> playerData) throws IOException {
        Map<Player, Position> positionMap = new HashMap<>();
        List<Player> turns = new ArrayList<>();

        for (String[] playerInfo : playerData) {
            for (String player : playerInfo) {
                String[] playerAttributes = player.split(" ");
                String name = playerAttributes[0];
                int x = Integer.parseInt(playerAttributes[1]);
                int y = Integer.parseInt(playerAttributes[2]);
                Strategy strategy = parseStrategy(playerAttributes[3]);
                Direction startingMove = parseDirection(playerAttributes[4]);
                Player newPlayer = new PlayerBot(name, strategy, startingMove);
                positionMap.put(newPlayer, new Position(x, y));
                players.add(newPlayer);
            }
        }

        this.playerPositionMap = positionMap;
        this.players = turns;
    }

    private Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            default -> throw new IOException("The strategy " + s + " is not supported.");
        };
    }

    private Direction parseDirection(String s) throws IOException {
        return switch (s) {
            case "UP" -> Direction.UP;
            case "DOWN" -> Direction.DOWN;
            case "LEFT" -> Direction.LEFT;
            case "RIGHT" -> Direction.RIGHT;
            default -> throw new IOException("The starting direction " + s + " is not supported.");
        };
    }

}