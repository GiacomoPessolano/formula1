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

import it.unicam.cs.giacomopessolano.formula1.exceptions.*;
import it.unicam.cs.giacomopessolano.formula1.grid.*;
import it.unicam.cs.giacomopessolano.formula1.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerStandardTest {
    private GameInitializer testInitializer;
    private Validator testValidator;
    private TurnManager testTurnManager;
    private PlayerFormula1 testPlayer1;
    private PlayerFormula1 testPlayer2;

    @BeforeEach
    void setUp() {
        Cell[][] gridArray = new Cell[3][3];
        gridArray[0][0] = new Cell(CellState.START);
        gridArray[0][1] = new Cell(CellState.START);
        gridArray[0][2] = new Cell(CellState.TRACK);
        gridArray[1][0] = new Cell(CellState.TRACK);
        gridArray[1][1] = new Cell(CellState.TRACK);
        gridArray[1][2] = new Cell(CellState.TRACK);
        gridArray[2][0] = new Cell(CellState.END);
        gridArray[2][1] = new Cell(CellState.END);
        gridArray[2][2] = new Cell(CellState.END);
        /*
        STE
        STE
        STE
         */
        ArrayGrid testGrid = new ArrayGrid(gridArray);
        testPlayer1 = new PlayerFormula1("Player1", new StrategyDumb(), Direction.CENTER);
        testPlayer2 = new PlayerFormula1("Player2", new StrategyDumb(), Direction.CENTER);
        Position testPosition1 = new Position(0, 0);
        Position testPosition2 = new Position(0, 1);

        testInitializer = new TestGameInitializer(
                testGrid,
                Arrays.asList(testPlayer1, testPlayer2),
                Map.of(testPlayer1, testPosition1, testPlayer2, testPosition2)
        );
        testValidator = new TestValidator();
        testTurnManager = new TestTurnManager();
    }

    @Test
    void testStartGame() {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer);
        gameManager.startGame();

        assertTrue(gameManager.isGameRunning());
        assertNull(gameManager.getWinner());
        assertEquals(testPlayer1, gameManager.getCurrentPlayer());
    }

    @Test
    void testBadValidation() {
        //the defined testValidator returns always false
        assertThrows(ValidationFailedException.class, () -> {
                GameManagerStandard gameManager = new GameManagerStandard(testInitializer, testValidator);
        });
    }

    @Test
    void testNextTurn() {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer);
        gameManager.startGame();

        gameManager.nextTurn(testTurnManager);

        //checks whether current player was updated
        assertEquals(testPlayer2, gameManager.getCurrentPlayer());
    }

    @Test
    void testPlayerCrash() {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer);
        gameManager.startGame();

        //makes players crash automatically
        ((TestTurnManager)testTurnManager).setNextCellState(CellState.END);
        gameManager.nextTurn(testTurnManager);

        assertEquals(testPlayer2, gameManager.getCurrentPlayer());
        gameManager.nextTurn(testTurnManager);
        assertFalse(gameManager.isGameRunning());
    }

    @Test
    void testGameEnd() {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer);
        gameManager.startGame();

        //acts as if player 1 reached the end
        ((TestTurnManager)testTurnManager).setNextCellState(CellState.END);
        gameManager.nextTurn(testTurnManager);

        assertFalse(gameManager.isGameRunning());
        assertEquals(testPlayer1, gameManager.getWinner());
    }

    //used to initialize a game without having to load it from a file
    private static class TestGameInitializer implements GameInitializer {
        private final Grid grid;
        private final List<Player> players;
        private final Map<Player, Position> playerPositions;

        public TestGameInitializer(Grid grid, List<Player> players, Map<Player, Position> playerPositions) {
            this.grid = grid;
            this.players = players;
            this.playerPositions = playerPositions;
        }

        @Override
        public Grid parseGrid() {
            return grid;
        }

        @Override
        public List<Player> parseTurns() {
            return players;
        }

        @Override
        public Map<Player, Position> parsePlayers() {
            return playerPositions;
        }
    }

    private static class TestValidator implements Validator {
        @Override
        public boolean performAllChecks() {
            return false; //always false for testing
        }
    }

    //for rapid testing, the turn manager returns always a cell of choice
    private static class TestTurnManager implements TurnManager {
        private CellState nextCellState = CellState.TRACK;

        @Override
        public CellState executeMove(Grid grid, Player player, Map<Player, Position> playerPositions) {
            return nextCellState;
        }

        public void setNextCellState(CellState nextCellState) {
            this.nextCellState = nextCellState;
        }
    }
}

