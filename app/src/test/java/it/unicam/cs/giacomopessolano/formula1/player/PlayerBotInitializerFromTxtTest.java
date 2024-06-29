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

package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBotInitializerFromTxtTest {

    private PlayerInitializerFromTxt initializer;

    @BeforeEach
    void setUp() {
        initializer = new PlayerBotInitializerFromTxt();
    }


    @Test
    void testInitialize() {
        String filename = "small.txt";
        try {
            initializer.initialize(filename);

            List<Player> players = initializer.getPlayers();
            Map<Player, Position> positions = initializer.getPositions();

            assertNotNull(players);
            assertNotNull(positions);
            assertEquals(2, players.size());
            assertEquals(2, positions.size());

            Player firstPlayer = players.get(0);
            Player secondPlayer = players.get(1);

            assertEquals("Zorro", firstPlayer.getName());
            assertEquals("Giacomo", secondPlayer.getName());

            Position firstPosition = positions.get(firstPlayer);
            Position secondPosition = positions.get(secondPlayer);

            assertEquals(new Move(0, 1), firstPlayer.getLastMove());
            assertEquals(new Move(0, -1), secondPlayer.getLastMove());

            assertEquals(1, firstPosition.x());
            assertEquals(1, firstPosition.y());
            assertEquals(1, secondPosition.x());
            assertEquals(2, secondPosition.y());

        } catch (UnrecognizedFileException | IncorrectConfigurationException e) {
            fail("Exception not expected: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInitializeWithWrongExtension() {
        String filename = "wrong.json";

        assertThrows(IncorrectConfigurationException.class, () -> initializer.initialize(filename));
    }

    @Test
    void testInitializeWithInvalidData() {
        String filename = "wrong.txt";

        assertThrows(IncorrectConfigurationException.class, () -> initializer.initialize(filename));
    }

    @Test
    void testInitializeWithInvalidFile() {
        String filename = "non-existent.txt";

        assertThrows(UnrecognizedFileException.class, () -> initializer.initialize(filename));
    }

}

