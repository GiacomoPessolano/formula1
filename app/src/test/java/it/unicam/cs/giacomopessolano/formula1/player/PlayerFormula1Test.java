package it.unicam.cs.giacomopessolano.formula1.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFormula1Test {

    private PlayerFormula1 player;
    private Strategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new StrategyDumb();
        player = new PlayerFormula1("TestPlayer", strategy, Direction.UP);
    }

    @Test
    void testGetName() {
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    void testGetStrategy() {
        assertEquals(strategy, player.getStrategy());
    }

    @Test
    void testCrash() {
        assertFalse(player.hasCrashed());
        player.crash();
        assertTrue(player.hasCrashed());
    }

    @Test
    void testGetLastMove() {
        //expected move should be x = 0, y = -1
        Move expectedMove = new Move(0, 0).update(Direction.UP);
        assertEquals(expectedMove, player.getLastMove());
    }

    @Test
    void testUpdateLastMove() {
        player.updateLastMove(Direction.RIGHT);
        Move expectedMove = new Move(0, 0).update(Direction.UP).update(Direction.RIGHT);
        assertEquals(expectedMove, player.getLastMove());
    }

    @Test
    void testEquals() {
        PlayerFormula1 samePlayer = new PlayerFormula1("TestPlayer", strategy, Direction.UP);
        PlayerFormula1 differentPlayer = new PlayerFormula1("DifferentPlayer", strategy, Direction.UP);
        assertEquals(player, samePlayer);
        assertNotEquals(player, differentPlayer);
    }

    @Test
    void testClone() {
        PlayerFormula1 clonedPlayer = player.clone();
        assertEquals(player, clonedPlayer);
        assertNotSame(player, clonedPlayer);
    }
}

