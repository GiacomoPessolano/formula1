package it.unicam.cs.giacomopessolano.formula1.grid;

import java.io.IOException;

public interface GridInitializer {
    Grid initializeGrid(String filename) throws IOException;
}
