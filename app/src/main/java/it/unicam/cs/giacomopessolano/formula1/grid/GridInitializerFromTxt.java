package it.unicam.cs.giacomopessolano.formula1.grid;

import java.io.IOException;

public interface GridInitializerFromTxt {

    void initialize(String filename) throws IOException;

    Grid getGrid();

    default void validateFileExtension(String filename) throws IOException {
        if (!filename.endsWith(".txt")) {
            throw new IOException("File must have a .txt extension.");
        }
    }
}