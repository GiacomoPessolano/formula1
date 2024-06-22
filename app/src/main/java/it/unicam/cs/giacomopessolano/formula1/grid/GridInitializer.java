package it.unicam.cs.giacomopessolano.formula1.grid;

import java.io.IOException;

//TODO javadoc

public interface GridInitializer {
    Grid initializeGrid(String filename) throws IOException;
}
