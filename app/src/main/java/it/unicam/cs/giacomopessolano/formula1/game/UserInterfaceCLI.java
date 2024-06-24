package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterfaceCLI implements UserInterface {

    GameManager manager;
    Scanner scanner;

    public UserInterfaceCLI(GameManager manager) {
        this.manager = manager;
        scanner = new Scanner(System.in);
    }

    @Override
    public String chooseTrack(String dirName) throws FileNotFoundException {
        List<String> tracks = availableTracks(dirName);
        if (tracks.isEmpty()) {
            System.out.println("No tracks found.");
            throw new FileNotFoundException();
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
            System.out.println("Track not found.");
            throw new  FileNotFoundException();
        }
    }

    @Override
    public void displayGrid() {
        Grid grid = manager.getGrid();
        int width = grid.getWidth();
        int height = grid.getHeight();
        StringBuilder gridToString = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridToString.append(parseCellState(grid.getCell(new Position(x, y))));
            }
            gridToString.append("\n");
        }

        System.out.println(gridToString.toString());

    }

    @Override
    public void gameOverMessage() {
        System.out.println("The game has ended.");
        if (manager.getWinner() == null) {
            System.out.println("Nobody won the game.");
        } else {
            System.out.println("The winner is " + manager.getWinner() + "!");
        }
    }

    @Override
    public boolean wantToPlayAgain() {
        System.out.println("Enter Y if you want to play again, anything else to exit.");
        String playAgain = scanner.nextLine().toUpperCase();

        if (playAgain.equals("Y")) {
            return true;
        } else {
            scanner.close();
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

    private String parseCellState(Cell cell) {
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
