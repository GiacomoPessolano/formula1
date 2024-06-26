package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Implementation of PlayerInitializerFromTxt that reads player information from an InputStream
 * and initializes a list of PlayerFormula1 and their positions. The List and the Map will use the
 * same pointers to access the Player.
 * <p>
 * The format of the input stream is as follows: the start of the grid is signaled by the string "PLAYERS"
 * and its end by an empty line; each player's attributes (NAME X-AXIS-POSITION Y-AXIS-POSITION
 * STRATEGY FIRST-MOVE) are specified on the same line, separated by spaces or tabs.
 * The permitted Strategies are DUMB;
 * the permitted First-Moves are UP, DOWN, LEFT, RIGHT.
 */
public class PlayerFormula1InitializerFromTxt implements PlayerInitializerFromTxt {

    /**
     * Parsed map of players to a Position.
     */
    private Map<Player, Position> playerPositionMap;
    /**
     * Parsed list of players.
     */
    private List<Player> players;

    /**
     * Parses player list and their positions from an InputStream created from the file's path.
     * The fields players and playerPositionMap are instantiated.
     *
     * @param filename InputStream containing player information.
     * @throws IOException If there is an error while reading the stream or file format is incorrect.
     */
    @Override
    public void initialize(String filename) throws IOException {
        validateFileExtension(filename);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new UnrecognizedFileException(filename);
        }

        List<String[]> playerData = readPlayerData(inputStream);
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
     * Reads and parses player data from the specified InputStream.
     * It starts reading from the line "PLAYERS".
     *
     * @param inputStream The InputStream containing player information.
     * @return A list of string arrays, each containing player attributes parsed from the InputStream.
     * @throws UnrecognizedFileException If there is an error while reading the stream or if its format is incorrect.
     */
    private List<String[]> readPlayerData(InputStream inputStream) throws IOException {
        List<String[]> playerData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean havePlayersStarted = false;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                if (!havePlayersStarted) {
                    havePlayersStarted = line.equals("PLAYERS");
                    continue;
                }

                String[] data = line.split(" ");
                playerData.add(data);
            }
        }

        if (playerData.isEmpty()) {
            throw new UnrecognizedFileException("The input stream does not contain valid player data.");
        }

        return playerData;
    }

    /**
     * Processes parsed player data to create Player objects and associate them with positions.
     *
     * @param playerData List of string arrays containing parsed player attributes.
     * @throws IncorrectConfigurationException If there is an error while parsing the player data or if it is incorrectly formatted.
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
            throw new IncorrectConfigurationException("The player data is incorrectly formatted.");
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
            default -> throw new IncorrectConfigurationException("The starting direction " + s + " is not supported.");
        };
    }

    /**
     * Compares a PlayerFormula1InitializerFromTxt to another object.
     *
     * @param obj Object to compare the initializer to.
     * @return True if the object has the same data structures, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerFormula1InitializerFromTxt other) {
            return this.playerPositionMap.equals(other.playerPositionMap) && this.players.equals(other.players);
        }
        return false;
    }

    /**
     * Returns hash value calculated on a PlayerFormula1InitializerFromTxt's data structures.
     *
     * @return A hash value for this initializer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerPositionMap, players);
    }
}
