package it.unicam.cs.giacomopessolano.formula1.grid;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class ArrayGridFromTxtInitializerTest {

    @Test
    void testInitializeGridFromFile_ValidFile() {
        ArrayGridFromTxtInitializer initializer = new ArrayGridFromTxtInitializer();
        try {
            Grid grid = initializer.initializeGrid("src/test/resources/small.txt");
            assertNotNull(grid);
            assertEquals(grid.getCell(1, 4).state, CellState.OFFROAD);
            assertEquals(grid.getCell(2, 4).state, CellState.OFFROAD);
            assertEquals(grid.getCell(4, 4).state, CellState.OFFROAD);
        } catch (IOException e) {
            fail("IOException should not be thrown for a valid file.");
        }
    }

    @Test
    void testInitializeGridFromFile_InvalidExtension() {
        ArrayGridFromTxtInitializer initializer = new ArrayGridFromTxtInitializer();
        assertThrows(IOException.class, () -> {
            initializer.initializeGrid("src/test/resources/wrong.json");
        });
    }

    @Test
    void testInitializeGridFromFile_InvalidSymbol() {
        ArrayGridFromTxtInitializer initializer = new ArrayGridFromTxtInitializer();
        assertThrows(IOException.class, () -> {
            initializer.initializeGrid("src/test/resources/wrong.txt");
        });
    }
}
