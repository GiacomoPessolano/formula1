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

package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//todo supplier for Strategy and Direction, playerFactory?

/**
 * Implementation of PlayerInitializerFromTxt that reads player information from a .txt file and
 * initializes a list of PlayerFormula1 and their positions. The List and the Map will use the
 * same pointers to access the Player.
 * <p>
 * The format of the .txt file is as follows: each player's attributes (NAME X-AXIS-POSITION Y-AXIS-POSITION
 * STRATEGY FIRST-MOVE) are specified on separate lines, separated by spaces or tabs.
 * The permitted Strategies are DUMB;
 * the permitted First-Moves are UP, DOWN, LEFT, RIGHT.
 */
public class PlayerFormula1InitializerFromTxt implements PlayerInitializerFromTxt {

    private Map<Player, Position> playerPositionMap;
    private List<Player> players;

    /**
     * Parses player list and their positions from a .txt file. The file is instantly validated to check
     * that it has the correct extension. The fields players and playerPositionMap are instantiated.
     *
     * @param filename Name of the .txt file.
     * @throws IOException If there is an error while reading the file or its format is incorrect.
     */
    @Override
    public void initialize(String filename) throws IOException {
        validateFileExtension(filename);
        List<String[]> playerData = readPlayerData(filename);
        findPlayers(playerData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Player, Position> getPositions() {
        return playerPositionMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Reads and parses player data from the specified .txt file.
     *
     * @param filename The name of the .txt file containing player information.
     * @return A list of string arrays, each containing player attributes parsed from the file.
     * @throws UnrecognizedFileException If there is an error while reading the file or if its format is incorrect.
     */
    private List<String[]> readPlayerData(String filename) throws IOException {
        List<String[]> playerData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while (!Objects.equals(line = br.readLine(), "TRACK") && line != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(" ");
                playerData.add(data);
            }
        } catch (FileNotFoundException e) {
            throw new UnrecognizedFileException(filename + " is not a valid file.");
        } catch (IOException e) {
            throw new UnrecognizedFileException("There was an error reading the file.");
        }

        return playerData;
    }

    /**
     * Processes parsed player data to create Player objects and associate them with positions.
     *
     * @param playerData List of string arrays containing parsed player attributes.
     * @throws IncorrectConfigurationException If there is an error while
     * parsing the player data or if it is incorrectly formatted.
     */
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
                Player newPlayer = new PlayerFormula1(name, strategy, startingMove);
                localPositionMap.put(newPlayer, new Position(x, y));
                localPlayerList.add(newPlayer);
            }
        } catch (Exception e) {
            throw new IncorrectConfigurationException("The file has not been prepared correctly.");
        }

        this.playerPositionMap = localPositionMap;
        this.players = localPlayerList;
    }

    /**
     * Parses a string representation of a strategy into an implementation of Strategy.
     *
     * @param s The string representation of the strategy.
     * @return A Strategy object corresponding to the parsed string.
     * @throws IncorrectConfigurationException If the strategy string is not recognized or supported.
     */
    private Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            default -> throw new IncorrectConfigurationException("The strategy " + s + " is not supported.");
        };
    }

    /**
     * Parses a string representation of a direction into a Direction object.
     *
     * @param s The string representation of the direction.
     * @return A Direction object corresponding to the parsed string.
     * @throws IncorrectConfigurationException If the direction string is not recognized or supported.
     */
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