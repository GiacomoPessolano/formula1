package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceCLI implements UserInterface {

    Scanner scanner;

    public UserInterfaceCLI() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String chooseTrack(String dirName) throws IOException {
        List<String> tracks = availableTracks(dirName);
        if (tracks.isEmpty()) {
            throw new UnrecognizedFileException("No tracks found.");
        }

        for (String track : tracks) {
            System.out.println(track);
        }

        System.out.println("Choose your track.");
        String trackChosen = scanner.nextLine();
        if (tracks.contains(trackChosen)) {
            System.out.println("Track chosen: " + trackChosen);
            return trackChosen;
        } else {
            throw new UnrecognizedFileException("Track not found.");
        }
    }

    @Override
    public void displayGrid(GameManager manager) {
        Grid grid = manager.getGrid();
        int width = grid.getWidth();
        int height = grid.getHeight();
        StringBuilder gridToString = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridToString.append(parseCellState(manager, grid.getCell(new Position(x, y))));
            }
            gridToString.append("\n");
        }

        System.out.println(gridToString.toString());

    }

    @Override
    public void turnMessage(GameManager manager) {
        Player player = manager.getCurrentPlayer();
        String name = player.getName();

        if (player.hasCrashed()) {
            System.out.println(name + " has crashed.");
        } else {
            System.out.println("It's " + name + ", " + manager.getId(player) + " , turn.");
        }
    }

    @Override
    public void gameOverMessage(GameManager manager) {
        System.out.println("The game has ended.");
        if (manager.getWinner() == null) {
            System.out.println("Nobody won the game.");
        } else {
            System.out.println("The winner is " + manager.getWinner() + "!");
        }
    }

    @Override
    public boolean wantToPlayAgainMessage() {
        System.out.println("Enter Y if you want to play again, anything else to exit.");
        String playAgain = scanner.nextLine().toUpperCase();

        if (playAgain.equals("Y")) {
            return true;
        } else {
            scanner.close();
            System.out.println("Goodbye...");
            return false;
        }
    }

    private List<String> availableTracks(String dirName) {
        List<String> tracks = new ArrayList<String>();
        File dir = new File(dirName);
        File[] files = dir.listFiles();

        if (files == null) {
            return tracks;
        }

        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                tracks.add(file.getName());
            }
        }

        return tracks;
    }

    private String parseCellState(GameManager manager, Cell cell) {
        if (cell.getPlayer() != null) return String.valueOf(manager.getId(cell.getPlayer()));

        switch (cell.getState()) {
            case END -> {
                return "@";
            }
            case OFFTRACK -> {
                return "X";
            }
            default -> {
                return " ";
            }
        }
    }
}
