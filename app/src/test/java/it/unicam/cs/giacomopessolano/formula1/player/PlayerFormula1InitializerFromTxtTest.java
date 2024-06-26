package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;
import it.unicam.cs.giacomopessolano.formula1.exceptions.UnrecognizedFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFormula1InitializerFromTxtTest {

    private PlayerInitializerFromTxt initializer;

    @BeforeEach
    void setUp() {
        initializer = new PlayerFormula1InitializerFromTxt();
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

