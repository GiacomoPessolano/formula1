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

package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.GridInitializerFromTxt;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.PlayerInitializerFromTxt;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of GameInitializer that works by uniting a GridInitializer and PlayerInitializer to
 * parse information from the same .txt file. The format of the file is defined by the two initializers,
 * but the two should mesh well to correctly define where the players and the grid begin.
 */
public class GameInitializerFromTxt implements GameInitializer {

    /**
     * Initializer responsible for the grid.
     */
    private final GridInitializerFromTxt gridInitializer;
    /**
     * Initializer responsible for the players.
     */
    private final PlayerInitializerFromTxt playerInitializer;

    /**
     * Constructs the initializer from a filename and the two specific initializers. The file is immediately
     * initialized and the resulting data structures are held by the two smaller initializers.
     *
     * @param filename Name of the file.
     * @param gridInitializer Initializer of the grid.
     * @param playerInitializer Initializer of the players.
     * @throws IOException If problems arise during the initializations.
     */
    public GameInitializerFromTxt(String filename, GridInitializerFromTxt gridInitializer,
                                  PlayerInitializerFromTxt playerInitializer) throws IOException {
        this.gridInitializer = gridInitializer;
        gridInitializer.initialize(filename);
        this.playerInitializer = playerInitializer;
        playerInitializer.initialize(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Player, Position> parsePlayers() {
        return playerInitializer.getPositions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> parseTurns() {
        return playerInitializer.getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Grid parseGrid() {
        return gridInitializer.getGrid();
    }

    /**
     * Compares a GameInitializerFromTxt to another object.
     *
     * @param obj Object to compare the initializer to.
     * @return True if the object has the same initializers.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameInitializerFromTxt other) {
            return gridInitializer.equals(other.gridInitializer) && playerInitializer.equals(other.playerInitializer);
        }
        return false;
    }

    /**
     * Returns hash value calculated on a GameInitializerFromTxt's two initializers.
     *
     * @return A hash value for this initializer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(gridInitializer, playerInitializer);
    }
}
