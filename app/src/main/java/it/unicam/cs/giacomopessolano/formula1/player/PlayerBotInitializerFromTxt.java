package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
            while (!Objects.equals(line = br.readLine(), "TRACK")) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(" ");
                playerData.add(data);
            }
        } catch (FileNotFoundException e) {
            throw new UnrecognizedFileException(filename + "is not a valid file.");
        } catch (IOException e) {
            throw new UnrecognizedFileException("There was an error reading the file.");
        }

        return playerData;
    }

    private void findPlayers(List<String[]> playerData) throws IOException {
        Map<Player, Position> localPositionMap = new HashMap<>();
        List<Player> localPlayerList = new ArrayList<>();

        try {
            for (String[] playerInfo : playerData) {
                String name = playerInfo[0];
                int x = Integer.parseInt(playerInfo[1]);
                int y = Integer.parseInt(playerInfo[2]);
                Strategy strategy = parseStrategy(playerInfo[3]);
                Direction startingMove = parseDirection(playerInfo[4]);
                Player newPlayer = new PlayerBot(name, strategy, startingMove);
                localPositionMap.put(newPlayer, new Position(x, y));
                localPlayerList.add(newPlayer);
            }
        } catch (Exception e) {
            throw new IncorrectConfigurationException("The file has not been prepared correctly.");
        }

        this.playerPositionMap = localPositionMap;
        this.players = localPlayerList;
    }

    private Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            default -> throw new IncorrectConfigurationException("The strategy " + s + " is not supported.");
        };
    }

    private Direction parseDirection(String s) throws IOException {
        return switch (s) {
            case "UP" -> Direction.UP;
            case "DOWN" -> Direction.DOWN;
            case "LEFT" -> Direction.LEFT;
            case "RIGHT" -> Direction.RIGHT;
            default -> throw new IncorrectConfigurationException(
                    "The starting direction " + s + " is not supported.");
        };
    }

}