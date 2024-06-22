package it.unicam.cs.giacomopessolano.formula1.game;

public interface GameStateChecker {

    void checkWinCondition(GameManager game);

    void nextTurn();
}
