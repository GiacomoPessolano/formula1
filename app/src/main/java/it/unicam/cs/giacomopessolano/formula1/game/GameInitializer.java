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
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.util.List;
import java.util.Map;

/**
 * Interface that initializes the game's data from a configuration file.
 */
public interface GameInitializer {

    /**
     * Returns Mapping of players to their starting positions. The players should be the same as the list.
     *
     * @return Map of players and their starting position.
     */
    Map<Player, Position> parsePlayers();

    /**
     * Returns list of all players. The players should be the same as the map.
     *
     * @return List of players.
     */
    List<Player> parseTurns();

    /**
     * Returns the game's grid.
     *
     * @return Track's representation as a Grid.
     */
    Grid parseGrid();
}
