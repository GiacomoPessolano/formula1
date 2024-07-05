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
    private ArrayGrid testGrid;
    private PlayerFormula1 testPlayer1;
    private PlayerFormula1 testPlayer2;
    private Position testPosition1;
    private Position testPosition2;

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
        testGrid = new ArrayGrid(gridArray);
        testPlayer1 = new PlayerFormula1("Player1", new StrategyDumb(), Direction.CENTER);
        testPlayer2 = new PlayerFormula1("Player2", new StrategyDumb(), Direction.CENTER);
        testPosition1 = new Position(0, 0);
        testPosition2 = new Position(0, 1);

        testInitializer = new TestGameInitializer(
                testGrid,
                Arrays.asList(testPlayer1, testPlayer2),
                Map.of(testPlayer1, testPosition1, testPlayer2, testPosition2)
        );
        testValidator = new TestValidator();
        testTurnManager = new TestTurnManager();
    }

    @Test
    void testStartGame() throws ValidationFailedException {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer, testValidator);
        gameManager.startGame();

        assertTrue(gameManager.isGameRunning());
        assertNull(gameManager.getWinner());
        assertEquals(testPlayer1, gameManager.getCurrentPlayer());
    }

    @Test
    void testNextTurn() throws ValidationFailedException {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer, testValidator);
        gameManager.startGame();

        gameManager.nextTurn(testTurnManager);

        //checks whether current player was updated
        assertEquals(testPlayer2, gameManager.getCurrentPlayer());
    }

    @Test
    void testPlayerCrash() throws ValidationFailedException {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer, testValidator);
        gameManager.startGame();

        //makes players crash automatically
        ((TestTurnManager)testTurnManager).setNextCellState(CellState.END);
        gameManager.nextTurn(testTurnManager);

        assertEquals(testPlayer2, gameManager.getCurrentPlayer());
        gameManager.nextTurn(testTurnManager);
        assertFalse(gameManager.isGameRunning());
    }

    @Test
    void testGameEnd() throws ValidationFailedException {
        GameManagerStandard gameManager = new GameManagerStandard(testInitializer, testValidator);
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
            return true; //always true for testing
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

