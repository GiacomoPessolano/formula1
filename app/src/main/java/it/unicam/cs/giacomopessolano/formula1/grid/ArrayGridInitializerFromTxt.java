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

package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation GridInitializerFromTxt that reads information from a .txt file and
 * initializes a grid.
 * <p>
 * The format of the .txt file is as follows: the start of the grid is signaled by the string "TRACK"
 * and its end by the end of the file; each cell of the grid must be represented with a character that
 * indicates the state (X->OFFTRACK, R->TRACK, S->START, E->END). A grid must be rectangular,
 * however if that is not the case the shorter rows will be filled with OFFTRACK cells.
 */
public class ArrayGridInitializerFromTxt implements GridInitializerFromTxt {

    private Grid grid;

    /**
     * Parses grid from a .txt file. The file is instantly validated to check that it has
     * the correct extension. The field grid is instantiated.
     *
     * @param filename Name of the .txt file.
     * @throws IOException If there is an error while reading the file or its format is incorrect.
     */
    @Override
    public void initialize(String filename) throws IOException {
        validateFileExtension(filename);

        List<char[]> rows = generateRows(filename);
        int width = findMaxWidth(rows);

        this.grid = createGrid(rows, width);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Grid getGrid()  {
        return this.grid;
    }

    /**
     * Reads the rows of characters representing the grid from the .txt file.
     * It starts reading from the line "TRACK".
     *
     * @param filename The name of the .txt file containing grid information.
     * @return A list of character arrays where each array represents a row of the grid.
     * @throws IOException If there is an error while reading the file.
     */
    private List<char[]> generateRows(String filename) throws IOException {
        List<char[]> rows = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        boolean hasGridStarted = false;
        while ((line = br.readLine()) != null) {
            if (!hasGridStarted) {
                hasGridStarted = line.equals("TRACK");
                continue;
            }
            rows.add(line.toCharArray());
        }

        return rows;
    }

    /**
     * Determines the maximum width among all rows of the grid.
     *
     * @param rows A list of character arrays where each array represents a row of the grid.
     * @return The maximum width among all rows.
     */
    private int findMaxWidth(List<char[]> rows) {
        int maxLength = 0;
        for (char[] row : rows) {
            if (maxLength < row.length) {
                maxLength = row.length;
            }
        }
        return maxLength;
    }

    /**
     * Creates a Grid object from the list of rows and the specified width. Rows shorter than
     * the width will be filled with OFFTRACK cells.
     *
     * @param rows  A list of character arrays where each array represents a row of the grid.
     * @param width The width of the grid (maximum row length).
     * @return A Grid object initialized based on the provided rows and width.
     * @throws IOException If there is an error while creating the grid.
     */
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

    /**
     * Converts a symbol character to a Cell object representing its state.
     *
     * @param symbol The symbol character representing the state of a cell.
     * @return A Cell object representing the state indicated by the symbol.
     * @throws IncorrectConfigurationException If the symbol does not correspond to a valid state.
     */
    private Cell symbolToCell(char symbol) throws IOException {
        //todo supplier?
        return switch (symbol) {
            case 'R' -> new Cell(CellState.TRACK);
            case 'S' -> new Cell(CellState.START);
            case 'E' -> new Cell(CellState.END);
            case 'X' -> new Cell(CellState.OFFTRACK);
            default -> throw new IncorrectConfigurationException("The symbol " + symbol + " is not valid.");
        };

    }
}