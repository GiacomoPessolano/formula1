package it.unicam.cs.giacomopessolano.formula1.grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO javadoc, testa se width o height = 0

public class ArrayGridFromTxtInitializer implements GridInitializer {
    @Override
    public Grid initializeGrid(String filename) throws IOException {
        if (!filename.endsWith(".txt")) {
            throw new IOException("File must have a .txt extension.");
        }

        List<char[]> rows = generateRows(filename);
        int width = findMaxWidth(rows);

        return createGrid(rows, width);
    }

    private ArrayList<char[]> generateRows(String filename) throws IOException {
        ArrayList<char[]> rows = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            rows.add(line.toCharArray());
        }

        return rows;
    }

    private int findMaxWidth(List<char[]> rows) {
        int maxLength = 0;
        for (char[] row : rows) {
            if (maxLength < row.length) {
                maxLength = row.length;
            }
        }
        return maxLength;
    }

    private Grid createGrid(List<char[]> rows, int width) throws IOException {
        Cell[][] grid = new Cell[rows.size()][width];

        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < width; j++) {
                if (j < rows.get(i).length) {
                    grid[i][j] = symbolToCell(rows.get(i)[j]);
                } else {
                    grid[i][j] = symbolToCell('X');
                }
            }
        }

        return new ArrayGrid(grid);
    }

    private Cell symbolToCell(char symbol) throws IOException {
        return switch (symbol) {
            case 'R' -> new Cell(CellState.ROAD);
            case 'S' -> new Cell(CellState.START);
            case 'E' -> new Cell(CellState.END);
            case 'O' -> new Cell(CellState.OILED);
            case 'X' -> new Cell(CellState.OFFROAD);
            default -> throw new IOException("The symbol " + symbol + " is not valid.");
        };

    }
}
