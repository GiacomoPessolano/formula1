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

import it.unicam.cs.giacomopessolano.formula1.exceptions.*;
import it.unicam.cs.giacomopessolano.formula1.player.Position;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayGridInitializerFromTxtTest {

    private ArrayGridInitializerFromTxt initializer;

    @BeforeEach
    void setUp() {
        initializer = new ArrayGridInitializerFromTxt();
    }

    @Test
    void testInitialize() {
        String validFileName = "small.txt";
        try {
            initializer.initialize(validFileName);
            Grid grid = initializer.getGrid();
            assertNotNull(grid);
            assertEquals(5, grid.getWidth());
            assertEquals(5, grid.getHeight());

            assertEquals(grid.getCell(new Position(4, 0)).getState(), CellState.OFFTRACK);
            assertEquals(grid.getCell(new Position(4, 1)).getState(), CellState.OFFTRACK);
            assertEquals(grid.getCell(new Position(4, 2)).getState(), CellState.OFFTRACK);
            assertEquals(grid.getCell(new Position(4, 3)).getState(), CellState.OFFTRACK);
            assertEquals(grid.getCell(new Position(4, 4)).getState(), CellState.OFFTRACK);
            assertEquals(grid.getCell(new Position(2, 0)).getState(), CellState.START);
            assertEquals(grid.getCell(new Position(3, 3)).getState(), CellState.END);
            assertEquals(grid.getCell(new Position(2, 2)).getState(), CellState.TRACK);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testInitializeWithIncorrectFileExtension() {
        String incorrectExtensionFileName = "wrong.json";
        assertThrows(IncorrectConfigurationException.class, () -> {initializer.initialize(incorrectExtensionFileName);});
    }

    @Test
    void testInitializeWithIncorrectConfiguration() {
        String incorrectConfigFileName = "wrong.txt";
        assertThrows(IncorrectConfigurationException.class, () ->
        {initializer.initialize(incorrectConfigFileName);});
    }

    @Test
    void testInitializeWithNonExistentFile() {
        String nonExistentFileName = "non-existent.txt";
        assertThrows(UnrecognizedFileException.class, () -> {initializer.initialize(nonExistentFileName);});

    }
}

